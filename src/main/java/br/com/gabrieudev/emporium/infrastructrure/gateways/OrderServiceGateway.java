package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.exception.StripeException;
import com.stripe.model.Coupon;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.exceptions.TransactionFailedException;
import br.com.gabrieudev.emporium.application.gateways.OrderGateway;
import br.com.gabrieudev.emporium.domain.entities.Discount;
import br.com.gabrieudev.emporium.domain.entities.Order;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartItemModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.DiscountModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.OrderModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.ProductModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartItemRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.DiscountRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.OrderRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.ProductRepository;

public class OrderServiceGateway implements OrderGateway {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final DiscountRepository discountRepository;

    public OrderServiceGateway(OrderRepository orderRepository, ProductRepository productRepository,
            CartItemRepository cartItemRepository, DiscountRepository discountRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    @CacheEvict(value = "Orders", key = "#order.id")
    @Transactional
    public Order create(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus("pending");
        order.setTotal(BigDecimal.ZERO);

        return orderRepository.save(OrderModel.from(order)).toDomainObj();
    }

    @Override
    public String generatePaymentLink(UUID orderId) {
        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado."));

        try {
            Session session = createSession(order.toDomainObj());
            return session.getUrl();
        } catch (StripeException e) {
            throw new TransactionFailedException("Erro ao salvar o pedido no Stripe: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll(Integer page, Integer size) {
        return orderRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(OrderModel::toDomainObj)
                .toList();
    }

    @Override
    @Cacheable(value = "Orders", key = "#id")
    @Transactional(readOnly = true)
    public Order findById(UUID id) {
        return orderRepository.findById(id)
                .map(OrderModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUserId(UUID userId, Integer page, Integer size) {
        Page<OrderModel> orders = orderRepository.findByUserId(userId, PageRequest.of(page, size));

        return orders.stream()
                .map(OrderModel::toDomainObj)
                .toList();
    }

    public void updatePayedOrder(Session session) {
        String orderId = session.getMetadata().get("orderId");
        Session.CustomerDetails customer = session.getCustomerDetails();

        OrderModel order = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        BigDecimal discount = new BigDecimal(session.getTotalDetails().getAmountDiscount()).movePointLeft(2);
        BigDecimal total = new BigDecimal(session.getAmountTotal()).movePointLeft(2);

        OrderModel updatedOrder = OrderModel.builder()
                .id(order.getId())
                .cart(order.getCart())
                .status("paid")
                .discount(discount)
                .total(total)
                .city(customer.getAddress().getCity())
                .country(customer.getAddress().getCountry())
                .postalCode(customer.getAddress().getPostalCode())
                .state(customer.getAddress().getState())
                .line1(customer.getAddress().getLine1())
                .line2(customer.getAddress().getLine2())
                .createdAt(order.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        orderRepository.save(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return orderRepository.existsById(id);
    }

    public Session createSession(Order order) throws StripeException {
        List<CartItemModel> cartItems = cartItemRepository.findByCartAndIsActiveTrue(CartModel.from(order.getCart()));
    
        List<Coupon> stripeCoupons = discountRepository.findByOrderId(order.getId())
                .stream()
                .map(DiscountModel::toDomainObj)
                .map(Discount::getCoupon)
                .map(coupon -> {
                    try {
                        return Coupon.retrieve(coupon.getStripeId());
                    } catch (StripeException e) {
                        throw new TransactionFailedException("Erro ao buscar o cupom no Stripe: " + e.getMessage());
                    }
                })
                .toList();
    
        SessionCreateParams.Discount discount = stripeCoupons.stream()
                .findFirst() 
                .map(coupon -> SessionCreateParams.Discount.builder()
                        .setCoupon(coupon.getId())
                        .build())
                .orElse(null); 
    
        List<SessionCreateParams.LineItem> lineItems = cartItems.stream()
                .map(item -> {
                    ProductModel productModel = productRepository.findById(item.getProduct().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    
                    Product stripeProduct;
                    try {
                        stripeProduct = Product.retrieve(productModel.getStripeId());
                    } catch (StripeException e) {
                        throw new TransactionFailedException("Erro ao buscar o produto no Stripe: " + e.getMessage());
                    }
    
                    String priceId = stripeProduct.getDefaultPrice();
    
                    return SessionCreateParams.LineItem.builder()
                            .setPrice(priceId)
                            .setQuantity((long) item.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());
    
        if (lineItems.isEmpty()) {
            throw new TransactionFailedException("Nenhum item de carrinho foi encontrado.");
        }
    
        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://emporium-production.up.railway.app/api/v1/swagger-ui/index.html#/")
                .setShippingAddressCollection(SessionCreateParams.ShippingAddressCollection.builder()
                        .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.BR)
                        .build())
                .addAllLineItem(lineItems)
                .putMetadata("orderId", order.getId().toString());
    
        if (discount != null) {
            sessionBuilder.addDiscount(discount);
        }
    
        return Session.create(sessionBuilder.build());
    }
    

}

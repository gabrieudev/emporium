package br.com.gabrieudev.emporium.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CartItemGateway;
import br.com.gabrieudev.emporium.application.gateways.StockGateway;
import br.com.gabrieudev.emporium.domain.entities.Cart;
import br.com.gabrieudev.emporium.domain.entities.CartItem;
import br.com.gabrieudev.emporium.domain.entities.Stock;

public class CartItemInteractor {
    private final CartItemGateway cartItemGateway;
    private final CartGateway cartGateway;
    private final StockGateway stockGateway;

    public CartItemInteractor(CartItemGateway cartItemGateway, CartGateway cartGateway, StockGateway stockGateway) {
        this.cartItemGateway = cartItemGateway;
        this.cartGateway = cartGateway;
        this.stockGateway = stockGateway;
    }

    public CartItem create(CartItem cartItem) {
        Cart cart = cartGateway.findById(cartItem.getCart().getId());

        Stock stock = stockGateway.findByProductId(cartItem.getProduct().getId());

        if (stock.getQuantity() < cartItem.getQuantity()) {
            throw new BusinessRuleException("Estoque insuficiente");
        }
        
        cartItem.setIsActive(Boolean.TRUE);

        cartItem.setTotal(cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));

        cart.setTotal(cart.getTotal().add(cartItem.getTotal()));

        cartGateway.update(cart);

        return cartItemGateway.create(cartItem);
    }

    public CartItem update(CartItem cartItem) {
        Cart cart = cartGateway.findById(cartItem.getCart().getId());

        cartItem.setTotal(cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));

        cart.setTotal(cart.getTotal().subtract(cartItem.getTotal()).add(cartItem.getTotal()));

        cartGateway.update(cart);

        return cartItemGateway.update(cartItem);
    }

    public void delete(UUID id) {
        CartItem cartItem = cartItemGateway.findById(id);

        Cart cart = cartGateway.findById(cartItem.getCart().getId());

        if (cartItem.getIsActive()) {
            cart.setTotal(cart.getTotal().subtract(cartItem.getTotal()));

            cartGateway.update(cart);
        }

        cartItemGateway.delete(id);
    }

    public CartItem findById(UUID id) {
        return cartItemGateway.findById(id);
    }

    public List<CartItem> findByCartId(UUID cartId, Integer page, Integer size) {
        return cartItemGateway.findByCartId(cartId, page, size);
    }

    public boolean existsById(UUID id) {
        return cartItemGateway.existsById(id);
    }

}

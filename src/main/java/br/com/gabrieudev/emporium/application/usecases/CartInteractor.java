package br.com.gabrieudev.emporium.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CartItemGateway;
import br.com.gabrieudev.emporium.application.gateways.StockGateway;
import br.com.gabrieudev.emporium.domain.entities.Cart;
import br.com.gabrieudev.emporium.domain.entities.CartItem;
import br.com.gabrieudev.emporium.domain.entities.Stock;

public class CartInteractor {
    private final CartGateway cartGateway;
    private final CartItemGateway cartItemGateway;
    private final StockGateway stockGateway;

    public CartInteractor(CartGateway cartGateway, CartItemGateway cartItemGateway, StockGateway stockGateway) {
        this.cartGateway = cartGateway;
        this.cartItemGateway = cartItemGateway;
        this.stockGateway = stockGateway;
    }

    public Cart findByToken(String token) {
        return cartGateway.findByToken(token);
    }

    public Cart findById(UUID id) {
        return cartGateway.findById(id);
    }

    public void clear (Cart cart) {
        List<CartItem> cartItems = cartItemGateway.findByCartId(cart.getId());

        cartItems.forEach(cartItem -> {
            Stock stock = stockGateway.findByProductId(cartItem.getProduct().getId());

            stock.setQuantity(stock.getQuantity() - cartItem.getQuantity());

            cartItem.setIsActive(Boolean.FALSE);

            cartItemGateway.update(cartItem);
            stockGateway.update(stock);
        });

        cart.setTotal(BigDecimal.ZERO);

        cartGateway.update(cart);
    }
}

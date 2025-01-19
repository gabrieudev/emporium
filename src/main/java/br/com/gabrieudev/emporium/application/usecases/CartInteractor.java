package br.com.gabrieudev.emporium.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CartItemGateway;
import br.com.gabrieudev.emporium.domain.entities.Cart;
import br.com.gabrieudev.emporium.domain.entities.CartItem;

public class CartInteractor {
    private final CartGateway cartGateway;
    private final CartItemGateway cartItemGateway;

    public CartInteractor(CartGateway cartGateway, CartItemGateway cartItemGateway) {
        this.cartGateway = cartGateway;
        this.cartItemGateway = cartItemGateway;
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
            cartItem.setIsActive(Boolean.FALSE);
            cartItemGateway.update(cartItem);
        });

        cart.setTotal(BigDecimal.ZERO);

        cartGateway.update(cart);
    }
}

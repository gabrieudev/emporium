package br.com.gabrieudev.emporium.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CartItemGateway;
import br.com.gabrieudev.emporium.domain.entities.Cart;
import br.com.gabrieudev.emporium.domain.entities.CartItem;

public class CartItemInteractor {
    private final CartItemGateway cartItemGateway;
    private final CartGateway cartGateway;

    public CartItemInteractor(CartItemGateway cartItemGateway, CartGateway cartGateway) {
        this.cartItemGateway = cartItemGateway;
        this.cartGateway = cartGateway;
    }

    public CartItem create(CartItem cartItem) {
        Cart cart = cartItem.getCart();
        
        cartItem.setIsActive(Boolean.TRUE);

        cartItem.setTotal(cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));

        cart.setTotal(cart.getTotal().add(cartItem.getTotal()));

        cartGateway.update(cart);

        return cartItemGateway.create(cartItem);
    }

    public CartItem update(CartItem cartItem) {
        Cart cart = cartItem.getCart();

        cartItem.setTotal(cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));

        cart.setTotal(cart.getTotal().subtract(cartItem.getTotal()).add(cartItem.getTotal()));

        cartGateway.update(cart);

        return cartItemGateway.update(cartItem);
    }

    public void delete(UUID id) {
        CartItem cartItem = cartItemGateway.findById(id);

        Cart cart = cartItem.getCart();

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

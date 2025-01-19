package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.CartItem;

public interface CartItemGateway {
    CartItem create(CartItem orderItem);
    CartItem update(CartItem orderItem);
    void delete(UUID id);
    CartItem findById(UUID id);
    boolean existsById(UUID id);
    List<CartItem> findByCartId(UUID cartId, Integer page, Integer size);
    List<CartItem> findByCartId(UUID cartId);
}

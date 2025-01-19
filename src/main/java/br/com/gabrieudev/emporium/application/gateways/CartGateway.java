package br.com.gabrieudev.emporium.application.gateways;

import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Cart;

public interface CartGateway {
    Cart findByToken(String token);
    Cart findById(UUID id);
    Cart create(Cart cart);
    Cart update(Cart cart);
}

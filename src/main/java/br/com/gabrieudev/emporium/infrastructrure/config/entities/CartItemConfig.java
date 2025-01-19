package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CartItemGateway;
import br.com.gabrieudev.emporium.application.usecases.CartItemInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.CartItemServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartItemRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartRepository;

@Configuration
public class CartItemConfig {
    @Bean
    CartItemInteractor cartItemInteractor(CartItemGateway cartItemGateway, CartGateway cartGateway) {
        return new CartItemInteractor(cartItemGateway, cartGateway);
    }

    @Bean
    CartItemGateway cartItemGateway(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        return new CartItemServiceGateway(cartItemRepository, cartRepository);
    }
}

package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CartItemGateway;
import br.com.gabrieudev.emporium.application.usecases.CartInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.CartServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartItemRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;

@Configuration
public class CartConfig {
    @Bean
    CartInteractor cartInteractor(CartGateway cartRepository, CartItemGateway cartItemGateway) {
        return new CartInteractor(cartRepository, cartItemGateway);
    }

    @Bean
    CartGateway cartGateway(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository, JwtDecoder jwtDecoder) {
        return new CartServiceGateway(cartRepository, cartItemRepository, userRepository, jwtDecoder);
    }
}

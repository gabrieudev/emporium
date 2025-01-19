package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.OrderGateway;
import br.com.gabrieudev.emporium.application.usecases.OrderInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.OrderServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartItemRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.DiscountRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.OrderRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.ProductRepository;

@Configuration
public class OrderConfig {
    @Bean
    OrderInteractor orderInteractor(OrderGateway orderGateway) {
        return new OrderInteractor(orderGateway);
    }

    @Bean
    OrderGateway orderGateway(OrderRepository orderRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, DiscountRepository discountRepository) {
        return new OrderServiceGateway(orderRepository, productRepository, cartItemRepository, discountRepository);
    }
}

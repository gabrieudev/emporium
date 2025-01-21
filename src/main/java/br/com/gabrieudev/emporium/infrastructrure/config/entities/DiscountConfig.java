package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CouponGateway;
import br.com.gabrieudev.emporium.application.gateways.DiscountGateway;
import br.com.gabrieudev.emporium.application.usecases.DiscountInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.DiscountServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.DiscountRepository;

@Configuration
public class DiscountConfig {
    @Bean
    DiscountInteractor discountInteractor(DiscountGateway discountGateway, CouponGateway couponGateway, CartGateway cartGateway) {
        return new DiscountInteractor(discountGateway, couponGateway, cartGateway);
    }

    @Bean
    DiscountGateway discountGateway(DiscountRepository discountRepository) {
        return new DiscountServiceGateway(discountRepository);
    }
}

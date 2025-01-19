package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.CouponGateway;
import br.com.gabrieudev.emporium.application.usecases.CouponInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.CouponServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CouponRepository;

@Configuration
public class CouponConfig {
    @Bean
    CouponInteractor couponInteractor(CouponGateway couponGateway) {
        return new CouponInteractor(couponGateway);
    }

    @Bean
    CouponGateway couponGateway(CouponRepository couponRepository) {
        return new CouponServiceGateway(couponRepository);
    }
}

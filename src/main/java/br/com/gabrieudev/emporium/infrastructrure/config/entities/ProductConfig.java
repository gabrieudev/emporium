package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.ProductGateway;
import br.com.gabrieudev.emporium.application.gateways.StockGateway;
import br.com.gabrieudev.emporium.application.usecases.ProductInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.ProductServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.ProductRepository;

@Configuration
public class ProductConfig {
    @Bean
    ProductInteractor productInteractor(ProductGateway productGateway, StockGateway stockGateway) {
        return new ProductInteractor(productGateway, stockGateway);
    }

    @Bean
    ProductGateway productGateway(ProductRepository productRepository) {
        return new ProductServiceGateway(productRepository);
    }
}

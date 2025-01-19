package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.StockGateway;
import br.com.gabrieudev.emporium.application.usecases.StockInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.StockServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.ProductRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.StockRepository;

@Configuration
public class StockConfig {
    @Bean
    StockInteractor stockInteractor(StockGateway stockGateway) {
        return new StockInteractor(stockGateway);
    }

    @Bean
    StockGateway stockGateway(StockRepository stockRepository, ProductRepository productRepository) {
        return new StockServiceGateway(stockRepository, productRepository);
    }
}

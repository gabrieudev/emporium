package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.CategoryGateway;
import br.com.gabrieudev.emporium.application.usecases.CategoryInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.CategoryServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CategoryRepository;

@Configuration
public class CategoryConfig {
    @Bean
    CategoryInteractor categoryInteractor(CategoryGateway categoryGateway) {
        return new CategoryInteractor(categoryGateway);
    }

    @Bean
    CategoryGateway categoryGateway(CategoryRepository categoryRepository) {
        return new CategoryServiceGateway(categoryRepository);
    }
}

package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.RoleGateway;
import br.com.gabrieudev.emporium.application.usecases.RoleInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.RoleServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.RoleRepository;

@Configuration
public class RoleConfig {
    @Bean
    RoleInteractor roleInteractor(RoleGateway roleGateway) {
        return new RoleInteractor(roleGateway);
    }

    @Bean
    RoleGateway roleGateway(RoleRepository roleRepository) {
        return new RoleServiceGateway(roleRepository);
    }
}

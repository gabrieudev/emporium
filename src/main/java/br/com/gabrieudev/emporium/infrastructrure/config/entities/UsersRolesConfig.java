package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.UserGateway;
import br.com.gabrieudev.emporium.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.emporium.application.usecases.UsersRolesInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.UsersRolesServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.RoleRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UsersRolesRepository;

@Configuration
public class UsersRolesConfig {
    @Bean
    UsersRolesInteractor usersRolesInteractor(UsersRolesGateway usersRolesGateway, UserGateway userGateway) {
        return new UsersRolesInteractor(usersRolesGateway, userGateway);
    }

    @Bean
    UsersRolesGateway usersRolesGateway(UsersRolesRepository usersRolesRepository, UserRepository userRepository, RoleRepository roleRepository) {
        return new UsersRolesServiceGateway(usersRolesRepository, userRepository, roleRepository);
    }
}

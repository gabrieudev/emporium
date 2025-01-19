package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.RoleGateway;
import br.com.gabrieudev.emporium.application.gateways.UserGateway;
import br.com.gabrieudev.emporium.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.emporium.application.usecases.UserInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.UserServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;

@Configuration
public class UserConfig {
    @Bean
    UserInteractor userInteractor(UserGateway userGateway, RoleGateway roleGateway, UsersRolesGateway usersRolesGateway, CartGateway cartGateway) {
        return new UserInteractor(userGateway, roleGateway, usersRolesGateway, cartGateway);
    }

    @Bean
    UserGateway userGateway(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtDecoder jwtDecoder) {
        return new UserServiceGateway(userRepository, passwordEncoder, jwtDecoder);
    }
}

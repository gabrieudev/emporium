package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import br.com.gabrieudev.emporium.application.gateways.AuthGateway;
import br.com.gabrieudev.emporium.application.gateways.UserGateway;
import br.com.gabrieudev.emporium.application.usecases.AuthInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.AuthServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UsersRolesRepository;

@Configuration
public class AuthConfig {
    @Bean
    AuthInteractor authInteractor(AuthGateway authGateway, UserGateway userGateway) {
        return new AuthInteractor(authGateway, userGateway);
    }

    @Bean
    AuthGateway authGateway(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder, UsersRolesRepository usersRolesRepository, UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        return new AuthServiceGateway(jwtDecoder, jwtEncoder, usersRolesRepository, userRepository, redisTemplate);
    }
}

package br.com.gabrieudev.emporium.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.emporium.application.gateways.AddressGateway;
import br.com.gabrieudev.emporium.application.usecases.AddressInteractor;
import br.com.gabrieudev.emporium.infrastructrure.gateways.AddressServiceGateway;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.AddressRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;

@Configuration
public class AddressConfig {
    @Bean
    AddressInteractor addressInteractor(AddressGateway addressGateway) {
        return new AddressInteractor(addressGateway);
    }

    @Bean
    AddressGateway addressGateway(AddressRepository addressRepository, UserRepository userRepository) {
        return new AddressServiceGateway(addressRepository, userRepository);
    }
}

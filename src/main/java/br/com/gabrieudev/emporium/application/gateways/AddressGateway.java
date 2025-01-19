package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Address;

public interface AddressGateway {
    Address create(Address address);
    Address update(Address address);
    void delete(UUID id);
    Address findById(UUID id);
    boolean existsById(UUID id);
    List<Address> findByUserId(UUID userId);
    List<Address> findByUserId(UUID userId, Integer page, Integer size);
}

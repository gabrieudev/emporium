package br.com.gabrieudev.emporium.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.gateways.AddressGateway;
import br.com.gabrieudev.emporium.domain.entities.Address;

public class AddressInteractor {
    private final AddressGateway addressGateway;

    public AddressInteractor(AddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    public Address create(Address address) {
        List<Address> addresses = addressGateway.findByUserId(address.getUser().getId());

        if (addresses.size() > 0 && address.getIsDefault()) {
            addresses.stream()
                    .filter(a -> a.getIsDefault())
                    .findFirst()
                    .ifPresent(a -> {
                        a.setIsDefault(Boolean.FALSE);
                        addressGateway.update(a);
                    });
        }

        return addressGateway.create(address);
    }

    public Address update(Address address) {
        List<Address> addresses = addressGateway.findByUserId(address.getUser().getId());

        if (addresses.size() > 0 && address.getIsDefault()) {
            addresses.stream()
                    .filter(a -> a.getIsDefault())
                    .findFirst()
                    .ifPresent(a -> {
                        a.setIsDefault(Boolean.FALSE);
                        addressGateway.update(a);
                    });
        }

        return addressGateway.update(address);
    }

    public void delete(UUID id) {
        addressGateway.delete(id);
    }

    public Address findById(UUID id) {
        return addressGateway.findById(id);
    }

    public boolean existsById(UUID id) {
        return addressGateway.existsById(id);
    }

    public List<Address> findByUserId(UUID userId) {
        return addressGateway.findByUserId(userId);
    }

    public List<Address> findByUserId(UUID userId, Integer page, Integer size) {
        return addressGateway.findByUserId(userId, page, size);
    }
}

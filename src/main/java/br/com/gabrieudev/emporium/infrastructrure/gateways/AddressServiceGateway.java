package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.gateways.AddressGateway;
import br.com.gabrieudev.emporium.domain.entities.Address;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.AddressModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.AddressRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;

public class AddressServiceGateway implements AddressGateway {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceGateway(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    @CacheEvict(value = "Addresses", key = "#id")
    @Transactional
    public Address create(Address address) {
        AddressModel addressModel = AddressModel.from(address);
        return addressRepository.save(addressModel).toDomainObj();
    }

    @Override
    @CacheEvict(value = "Addresses", key = "#id")
    @Transactional
    public void delete(UUID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Endereco não encontrado");
        }

        addressRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return addressRepository.existsById(id);
    }

    @Override
    @Cacheable(value = "Addresses", key = "#id")
    @Transactional(readOnly = true)
    public Address findById(UUID id) {
        return addressRepository.findById(id)
                .map(AddressModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findByUserId(UUID userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        return addressRepository.findByUser(user)
                .stream()
                .map(AddressModel::toDomainObj)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findByUserId(UUID userId, Integer page, Integer size) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        return addressRepository.findByUser(user, PageRequest.of(page, size))
                .stream()
                .map(AddressModel::toDomainObj)
                .toList();
    }

    @Override
    @CacheEvict(value = "Addresses", key = "#address.id")
    @Transactional
    public Address update(Address address) {
        AddressModel addressModel = addressRepository.findById(address.getId())
                .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado"));

        addressModel.update(address);

        return addressRepository.save(addressModel).toDomainObj();
    }
    
}

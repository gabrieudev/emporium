package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.gateways.RoleGateway;
import br.com.gabrieudev.emporium.domain.entities.Role;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.RoleRepository;

@Service
public class RoleServiceGateway implements RoleGateway {
    private final RoleRepository roleRepository;

    public RoleServiceGateway(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return roleRepository.existsById(id);
    }

    @Override
    @CacheEvict(value = "Roles", key = "#role.id")
    @Transactional
    public Role update(Role role) {
        RoleModel roleModel = roleRepository.findById(role.getId())
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));

        roleModel.update(role);

        return roleRepository.save(roleModel).toDomainObj();
    }

    @Override
    @CacheEvict(value = "Roles", key = "#id")
    @Transactional
    public void delete(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role não encontrada");
        }
        roleRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "Roles", key = "#id")
    @Transactional(readOnly = true)
    public Role findById(UUID id) {
        return roleRepository.findById(id)
            .map(RoleModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles(Integer page, Integer size) {
        return roleRepository.findAll(PageRequest.of(page, size))
            .stream()
            .map(RoleModel::toDomainObj)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRolesByUserId(UUID userId) {
        return roleRepository.findAllRolesByUserId(userId)
            .stream()
            .map(RoleModel::toDomainObj)
            .toList();
    }

    @Override
    @CacheEvict(value = "Roles", key = "#role.id")
    @Transactional
    public Role create(Role role) {
        return roleRepository.save(RoleModel.from(role)).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByName(String name) {
        return roleRepository.findByName(name)
            .map(RoleModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}

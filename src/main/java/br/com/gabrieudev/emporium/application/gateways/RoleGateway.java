package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Role;

public interface RoleGateway {
    Role create(Role role);
    Role update(Role role);
    Role findById(UUID id);
    Role findByName(String name);
    boolean existsById(UUID id);
    boolean existsByName(String name);
    void delete(UUID id);
    List<Role> getAllRoles(Integer page, Integer size);
    List<Role> getAllRolesByUserId(UUID userId);
}

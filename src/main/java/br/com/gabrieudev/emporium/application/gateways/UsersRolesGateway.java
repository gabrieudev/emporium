package br.com.gabrieudev.emporium.application.gateways;

import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.UsersRoles;

public interface UsersRolesGateway {
    UsersRoles create(UsersRoles usersRoles);
    void deleteByUserIdAndRoleId(UUID userId, UUID roleId);
    boolean existsById(UUID id);
    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);
    UsersRoles findById(UUID id);
}

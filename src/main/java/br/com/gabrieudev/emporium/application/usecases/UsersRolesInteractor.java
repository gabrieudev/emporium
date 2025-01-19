package br.com.gabrieudev.emporium.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.emporium.application.gateways.UserGateway;
import br.com.gabrieudev.emporium.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.emporium.domain.entities.Role;
import br.com.gabrieudev.emporium.domain.entities.User;
import br.com.gabrieudev.emporium.domain.entities.UsersRoles;

public class UsersRolesInteractor {
    private final UsersRolesGateway usersRolesGateway;
    private final UserGateway userGateway;

    public UsersRolesInteractor(UsersRolesGateway usersRolesGateway, UserGateway userGateway) {
        this.usersRolesGateway = usersRolesGateway;
        this.userGateway = userGateway;
    }

    public void deleteByUserIdAndRoles(UUID userId, List<Role> roles) {
        roles.forEach(
            role -> deleteByUserIdAndRoleId(userId, role.getId())
        );
    }

    public void createByUserIdAndRoles(UUID userId, List<Role> roles) {
        User user = userGateway.findById(userId);
        roles.forEach(
            role -> create(new UsersRoles(null, user, role))
        );   
    }

    public void deleteByUserIdAndRoleId(UUID userId, UUID roleId) {
        usersRolesGateway.deleteByUserIdAndRoleId(userId, roleId);
    }

    public boolean existsByUserIdAndRoleId(UUID userId, UUID roleId) {
        return usersRolesGateway.existsByUserIdAndRoleId(userId, roleId);
    }

    public UsersRoles create(UsersRoles usersRoles) {
        if (existsByUserIdAndRoleId(usersRoles.getUser().getId(), usersRoles.getRole().getId())) {
            throw new EntityAlreadyExistsException("Usuário já possui a role " + usersRoles.getRole().getName());
        }
        return usersRolesGateway.create(usersRoles);
    }

    public UsersRoles findById(UUID id) {
        return usersRolesGateway.findById(id);
    }

    public boolean existsById(UUID id) {
        return usersRolesGateway.existsById(id);
    }
}

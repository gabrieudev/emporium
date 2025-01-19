package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.emporium.domain.entities.UsersRoles;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UsersRolesModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.RoleRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UsersRolesRepository;

@Service
public class UsersRolesServiceGateway implements UsersRolesGateway {
    private final UsersRolesRepository usersRolesRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UsersRolesServiceGateway(UsersRolesRepository usersRolesRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.usersRolesRepository = usersRolesRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void deleteByUserIdAndRoleId(UUID userId, UUID roleId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        RoleModel role = roleRepository.findById(roleId)
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));

        if (!existsByUserIdAndRoleId(userId, roleId)) {
            throw new EntityNotFoundException("Usuário não possui esta role");
        }

        usersRolesRepository.deleteByUserAndRole(user, role);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserIdAndRoleId(UUID userId, UUID roleId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        RoleModel role = roleRepository.findById(roleId)
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));

        return usersRolesRepository.existsByUserAndRole(user, role);
    }

    @Override
    @Transactional
    public UsersRoles create(UsersRoles usersRoles) {
        return usersRolesRepository.save(UsersRolesModel.from(usersRoles)).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return usersRolesRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsersRoles findById(UUID id) {
        return usersRolesRepository.findById(id)
            .map(UsersRolesModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não possui esta role"));
    }
}

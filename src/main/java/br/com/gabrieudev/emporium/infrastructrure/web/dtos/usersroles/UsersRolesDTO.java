package br.com.gabrieudev.emporium.infrastructrure.web.dtos.usersroles;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.UsersRoles;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.role.RoleDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.user.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class UsersRolesDTO {
    @Schema(
        description = "Identificador da relação de usuário e role",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    private UUID id;

    @Schema(
        description = "Identificador do usuário",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    @NotNull(message = "Usuário obrigatório")
    private UserDTO user;

    @Schema(
        description = "Identificador da Role",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    @NotNull(message = "Role obrigatória")
    private RoleDTO role;

    public static UsersRolesDTO from(UsersRoles usersRoles) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(usersRoles, UsersRolesDTO.class);
    }

    public UsersRoles toDomainObj() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsersRoles.class);
    }
}

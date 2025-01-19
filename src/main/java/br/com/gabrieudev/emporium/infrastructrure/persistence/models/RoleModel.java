package br.com.gabrieudev.emporium.infrastructrure.persistence.models;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "roles")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public Role toDomainObj() {
        return new ModelMapper().map(this, Role.class);
    }

    public void update(Role role) {
        new ModelMapper().map(role, this);
    }

    public static RoleModel from(Role role) {
        return new ModelMapper().map(role, RoleModel.class);
    }
}
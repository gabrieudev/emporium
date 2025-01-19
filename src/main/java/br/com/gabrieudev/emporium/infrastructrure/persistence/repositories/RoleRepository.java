package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    @Query(
        value = """
                SELECT r.* 
                FROM users_roles ur
                INNER JOIN roles r 
                    ON ur.role_id = r.id
                WHERE ur.user_id = :p1
                """,
        nativeQuery = true
    )
    List<RoleModel> findAllRolesByUserId(@Param("p1") UUID userId);

    Optional<RoleModel> findByName(String name);

    boolean existsByName(String name);
}
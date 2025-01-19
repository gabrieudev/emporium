package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);

    @Query(
        value = """
                SELECT * FROM users
                WHERE
                    :p1 IS NULL
                    OR (email LIKE :p1) 
                    OR (first_name LIKE :p1) 
                    OR (last_name LIKE :p1)
                """,
        nativeQuery = true
    )
    Page<UserModel> search(
        @Param("p1") String param, 
        Pageable pageable
    );
}
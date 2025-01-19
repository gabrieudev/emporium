package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    @Query(
        value = """
                SELECT * FROM products
                WHERE
                    :p1 IS NULL
                    OR (name LIKE :p1)
                    OR (description LIKE :p1)
                """,
        nativeQuery = true
    )
    Page<ProductModel> search(
        @Param("p1") String param, 
        Pageable pageable
    );
}

package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.DiscountModel;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountModel, UUID> {
    @Query(
        value = """
                SELECT d.* 
                FROM discounts d
                INNER JOIN orders o 
                    ON d.order_id = o.id
                INNER JOIN carts c
                    ON o.cart_id = c.id
                INNER JOIN cart_items ci
                    ON c.id = ci.cart_id
                WHERE ci.is_active = true AND o.id = :p1
                """,
        nativeQuery = true
    )
    List<DiscountModel> findByOrderId(@Param("p1") UUID orderId);
}

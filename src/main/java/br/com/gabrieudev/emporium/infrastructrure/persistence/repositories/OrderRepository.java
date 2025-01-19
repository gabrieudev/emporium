package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.OrderModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    List<OrderModel> findByCart(CartModel cart);
    
    @Query(
        value = """
                SELECT o.* 
                FROM orders o
                INNER JOIN carts c
                    ON o.cart_id = c.id
                WHERE c.user_id = :p1
                """,
        nativeQuery = true
    )
    Page<OrderModel> findByUserId(
        @Param("p1") UUID userId, 
        Pageable pageable
    );
}

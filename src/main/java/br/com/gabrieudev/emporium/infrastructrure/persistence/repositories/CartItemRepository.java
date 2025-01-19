package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartItemModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartModel;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemModel, UUID> {
    List<CartItemModel> findByCartAndIsActiveTrue(CartModel cart);
    Page<CartItemModel> findByCartAndIsActiveTrue(CartModel cart, Pageable pageable);
}

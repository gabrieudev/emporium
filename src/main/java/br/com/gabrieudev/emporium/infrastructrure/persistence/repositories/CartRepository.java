package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;

@Repository
public interface CartRepository extends JpaRepository<CartModel, UUID> {
    Optional<CartModel> findByUser(UserModel user);
}

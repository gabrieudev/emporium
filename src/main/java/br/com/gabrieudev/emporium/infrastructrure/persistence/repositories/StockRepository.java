package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.ProductModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.StockModel;

@Repository
public interface StockRepository extends JpaRepository<StockModel, UUID> {
    Optional<StockModel> findByProduct(ProductModel product);
}

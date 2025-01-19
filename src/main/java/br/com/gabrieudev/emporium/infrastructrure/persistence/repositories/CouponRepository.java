package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CouponModel;

@Repository
public interface CouponRepository extends JpaRepository<CouponModel, UUID> {
    Optional<CouponModel> findByCode(String code);
    boolean existsByCode(String code);
}

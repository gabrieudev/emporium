package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.AddressModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, UUID> {
    List<AddressModel> findByUser(UserModel user);
    Page<AddressModel> findByUser(UserModel user, Pageable pageable);
}

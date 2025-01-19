package br.com.gabrieudev.emporium.infrastructrure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CategoryModel;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    
}

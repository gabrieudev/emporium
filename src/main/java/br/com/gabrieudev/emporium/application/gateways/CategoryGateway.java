package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Category;

public interface CategoryGateway {
    Category create(Category category);
    Category update(Category category);
    Category findById(UUID id);
    boolean existsById(UUID id);
    void delete(UUID id);
    List<Category> findAll(Integer page, Integer size);
}

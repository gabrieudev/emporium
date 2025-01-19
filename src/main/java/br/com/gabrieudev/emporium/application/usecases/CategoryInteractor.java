package br.com.gabrieudev.emporium.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.gateways.CategoryGateway;
import br.com.gabrieudev.emporium.domain.entities.Category;

public class CategoryInteractor {
    private final CategoryGateway categoryGateway;

    public CategoryInteractor(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    public Category create(Category category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        return categoryGateway.create(category);
    }

    public Category update(Category category) {
        category.setUpdatedAt(LocalDateTime.now());

        return categoryGateway.update(category);
    }

    public Category findById(UUID id) {
        return categoryGateway.findById(id);
    }

    public void delete(UUID id) {
        categoryGateway.delete(id);
    }

    public List<Category> findAll(Integer page, Integer size) {
        return categoryGateway.findAll(page, size);
    }

    public boolean existsById(UUID id) {
        return categoryGateway.existsById(id);
    }
}

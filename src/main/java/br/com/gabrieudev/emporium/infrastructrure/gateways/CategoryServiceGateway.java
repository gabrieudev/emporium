package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.gateways.CategoryGateway;
import br.com.gabrieudev.emporium.domain.entities.Category;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CategoryModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CategoryRepository;

public class CategoryServiceGateway implements CategoryGateway {
    private final CategoryRepository categoryRepository;

    public CategoryServiceGateway(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @CacheEvict(value = "Categories", key = "#category.id")
    @Transactional
    public Category create(Category category) {
        CategoryModel categoryModel = CategoryModel.from(category);
        return categoryRepository.save(categoryModel).toDomainObj();
    }

    @Override
    @CacheEvict(value = "Categories", key = "#id")
    @Transactional
    public void delete(UUID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Categoria nao encontrada");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll(Integer page, Integer size) {
        return categoryRepository.findAll(PageRequest.of(page, size)).stream()
                .map(CategoryModel::toDomainObj)
                .toList();
    }

    @Override
    @Cacheable(value = "Categories", key = "#id")
    @Transactional(readOnly = true)
    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .map(CategoryModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada"));
    }

    @Override
    @CacheEvict(value = "Categories", key = "#category.id")
    @Transactional
    public Category update(Category category) {
        CategoryModel categoryModel = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada"));

        categoryModel.update(category);

        return categoryRepository.save(categoryModel).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return categoryRepository.existsById(id);
    }

}

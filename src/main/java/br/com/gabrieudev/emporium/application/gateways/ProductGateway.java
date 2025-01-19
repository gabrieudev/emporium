package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Product;

public interface ProductGateway {
    Product create(Product product);
    Product update(Product product);
    Product findById(UUID id);
    boolean existsById(UUID id);
    void delete(UUID id);
    List<Product> search(String param, Integer page, Integer size);
}

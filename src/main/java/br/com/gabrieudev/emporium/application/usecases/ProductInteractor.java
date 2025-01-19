package br.com.gabrieudev.emporium.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.gateways.ProductGateway;
import br.com.gabrieudev.emporium.application.gateways.StockGateway;
import br.com.gabrieudev.emporium.domain.entities.Product;
import br.com.gabrieudev.emporium.domain.entities.Stock;

public class ProductInteractor {
    private final ProductGateway productGateway;
    private final StockGateway stockGateway;

    public ProductInteractor(ProductGateway productGateway, StockGateway stockGateway) {
        this.productGateway = productGateway;
        this.stockGateway = stockGateway;
    }

    public Product create(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return productGateway.create(product);
    }

    public Product update(Product product) {
        product.setUpdatedAt(LocalDateTime.now());

        return productGateway.update(product);
    }

    public Product findById(UUID id) {
        return productGateway.findById(id);
    }

    public void delete(UUID id) {
        Stock stock = stockGateway.findByProductId(id);
        stockGateway.delete(stock.getId());

        productGateway.delete(id);
    }

    public List<Product> search(String param, Integer page, Integer size) {
        return productGateway.search(param, page, size);
    }
}

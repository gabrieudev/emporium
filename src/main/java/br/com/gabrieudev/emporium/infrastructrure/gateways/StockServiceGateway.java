package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.gateways.StockGateway;
import br.com.gabrieudev.emporium.domain.entities.Stock;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.ProductModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.StockModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.ProductRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.StockRepository;

public class StockServiceGateway implements StockGateway {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public StockServiceGateway(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    @Override
    @CacheEvict(value = "Stocks", key = "#stock.id")
    @Transactional
    public Stock create(Stock stock) {
        StockModel stockModel = StockModel.fromDomainObj(stock);
        return stockRepository.save(stockModel).toDomainObj();
    }

    @Override
    @CacheEvict(value = "Stocks", key = "#id")
    @Transactional
    public void delete(UUID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Estoque não encontrado");
        }

        stockRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return stockRepository.existsById(id);
    }

    @Override
    @Cacheable(value = "Stocks", key = "#id")
    @Transactional(readOnly = true)
    public Stock findById(UUID id) {
        return stockRepository.findById(id)
                .map(StockModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Stock findByProductId(UUID productId) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        return stockRepository.findByProduct(product)
                .map(StockModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado"));
    }

    @Override
    @CacheEvict(value = "Stocks", key = "#stock.id")
    @Transactional
    public Stock update(Stock stock) {
        StockModel stockModel = stockRepository.findById(stock.getId())
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado"));

        stockModel.update(stock);

        return stockRepository.save(stockModel).toDomainObj();
    }
    
}

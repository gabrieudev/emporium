package br.com.gabrieudev.emporium.application.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.gateways.StockGateway;
import br.com.gabrieudev.emporium.domain.entities.Stock;

public class StockInteractor {
    private final StockGateway stockGateway;

    public StockInteractor(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    public Stock create(Stock stock) {
        stock.setCreatedAt(LocalDateTime.now());
        stock.setUpdatedAt(LocalDateTime.now());

        return stockGateway.create(stock);
    }

    public Stock update(Stock stock) {
        stock.setUpdatedAt(LocalDateTime.now());

        return stockGateway.update(stock);
    }

    public Stock findById(UUID id) {
        return stockGateway.findById(id);
    }

    public void delete(UUID id) {
        stockGateway.delete(id);
    }

    public boolean existsById(UUID id) {
        return stockGateway.existsById(id);
    }

    public Stock findByProductId(UUID productId) {
        return stockGateway.findByProductId(productId);
    }
}

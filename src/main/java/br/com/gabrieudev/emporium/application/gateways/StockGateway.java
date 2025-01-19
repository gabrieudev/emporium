package br.com.gabrieudev.emporium.application.gateways;

import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Stock;

public interface StockGateway {
    Stock create(Stock stock);
    Stock update(Stock stock);
    Stock findById(UUID id);
    boolean existsById(UUID id);
    void delete(UUID id);
    Stock findByProductId(UUID productId);
}

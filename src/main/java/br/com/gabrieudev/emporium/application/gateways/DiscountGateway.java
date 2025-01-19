package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Discount;

public interface DiscountGateway {
    Discount create(Discount discount);
    void delete(UUID id);
    Discount findById(UUID id);
    List<Discount> findByOrderId(UUID cartId);
}

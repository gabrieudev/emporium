package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.gateways.DiscountGateway;
import br.com.gabrieudev.emporium.domain.entities.Discount;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.DiscountModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.DiscountRepository;

public class DiscountServiceGateway implements DiscountGateway {
    private final DiscountRepository discountRepository;

    public DiscountServiceGateway(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Discount findById(UUID id) {
        return discountRepository.findById(id)
                .map(DiscountModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado"));
    }

    @Override
    @Transactional
    public Discount create(Discount discount) {
        return discountRepository.save(DiscountModel.from(discount)).toDomainObj();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        discountRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discount> findByOrderId(UUID cartId) {
        return discountRepository.findByOrderId(cartId).stream()
            .map(DiscountModel::toDomainObj)
            .toList();
    }
}

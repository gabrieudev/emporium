package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.exception.StripeException;
import com.stripe.param.CouponCreateParams;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.exceptions.TransactionFailedException;
import br.com.gabrieudev.emporium.application.gateways.CouponGateway;
import br.com.gabrieudev.emporium.domain.entities.Coupon;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CouponModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CouponRepository;

public class CouponServiceGateway implements CouponGateway {
    private final CouponRepository couponRepository;

    public CouponServiceGateway(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    @CacheEvict(value = "Coupons", key = "#coupon.id")
    @Transactional
    public Coupon create(Coupon coupon) {
        try {
            long monthsBetween = ChronoUnit.MONTHS.between(coupon.getValidFrom().toLocalDate(), coupon.getValidUntil().toLocalDate());

            if (monthsBetween <= 0) {
                throw new TransactionFailedException("Datas inválidas.");
            }

            CouponCreateParams params = CouponCreateParams.builder()
                    .setDuration(CouponCreateParams.Duration.REPEATING)
                    .setDurationInMonths(monthsBetween)
                    .setPercentOff(coupon.getPercentOff())
                    .setMaxRedemptions(coupon.getUsageLimit())
                    .build();

            com.stripe.model.Coupon couponStripe = com.stripe.model.Coupon.create(params);

            coupon.setStripeId(couponStripe.getId());

            return couponRepository.save(CouponModel.from(coupon)).toDomainObj();
        } catch (StripeException e) {
            throw new TransactionFailedException("Erro ao salvar o cupom no Stripe: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return couponRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coupon> findAll(Integer page, Integer size) {
        return couponRepository.findAll(PageRequest.of(page, size)).stream()
                .map(CouponModel::toDomainObj)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon findByCode(String code) {
        return couponRepository.findByCode(code)
                .map(CouponModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado."));
    }

    @Override
    @Cacheable(value = "Coupons", key = "#id")
    @Transactional(readOnly = true)
    public Coupon findById(UUID id) {
        return couponRepository.findById(id)
                .map(CouponModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return couponRepository.existsByCode(code);
    }

    @Override
    @CacheEvict(value = "Coupons", key = "#coupon.id")
    @Transactional
    public Coupon update(Coupon coupon) {
        CouponModel couponToUpdate = couponRepository.findById(coupon.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cupom nao encontrado."));

        couponToUpdate.update(coupon);

        return couponRepository.save(couponToUpdate).toDomainObj();
    }

}

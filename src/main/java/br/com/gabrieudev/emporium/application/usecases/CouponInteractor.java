package br.com.gabrieudev.emporium.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.emporium.application.gateways.CouponGateway;
import br.com.gabrieudev.emporium.domain.entities.Coupon;

public class CouponInteractor {
    private final CouponGateway couponGateway;
    
    public CouponInteractor(CouponGateway couponGateway) {
        this.couponGateway = couponGateway;
    }

    public Coupon create(Coupon coupon) {
        if (existsByCode(coupon.getCode())) {
            throw new EntityAlreadyExistsException("Já existe um cupom com esse código: " + coupon.getCode());
        }

        coupon.setCreatedAt(LocalDateTime.now());
        coupon.setUpdatedAt(LocalDateTime.now());
        coupon.setUsageCount(0L);

        return couponGateway.create(coupon);
    }

    public Coupon update(Coupon coupon) {
        Coupon existingCoupon = findById(coupon.getId());

        if (!existingCoupon.getCode().equals(coupon.getCode()) && existsByCode(coupon.getCode())) {
            throw new EntityAlreadyExistsException("Já existe um cupom com esse código");
        }

        coupon.setUpdatedAt(LocalDateTime.now());

        return couponGateway.update(coupon);
    }

    public boolean existsByCode(String code) {
        return couponGateway.existsByCode(code);
    }

    public Coupon findById(UUID id) {
        return couponGateway.findById(id);
    }

    public Coupon findByCode(String code) {
        return couponGateway.findByCode(code);
    }

    public boolean existsById(UUID id) {
        return couponGateway.existsById(id);
    }

    public List<Coupon> findAll(Integer page, Integer size) {
        return couponGateway.findAll(page, size);
    }
}

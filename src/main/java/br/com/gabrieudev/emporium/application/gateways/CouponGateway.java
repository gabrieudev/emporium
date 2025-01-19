package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.domain.entities.Coupon;

public interface CouponGateway {
    Coupon create(Coupon coupon);
    Coupon update(Coupon coupon);
    Coupon findById(UUID id);
    boolean existsById(UUID id);
    Coupon findByCode(String code);
    boolean existsByCode(String code);
    List<Coupon> findAll(Integer page, Integer size);
}

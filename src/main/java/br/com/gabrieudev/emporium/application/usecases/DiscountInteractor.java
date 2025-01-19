package br.com.gabrieudev.emporium.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.exceptions.InvalidCouponException;
import br.com.gabrieudev.emporium.application.gateways.CouponGateway;
import br.com.gabrieudev.emporium.application.gateways.DiscountGateway;
import br.com.gabrieudev.emporium.domain.entities.Coupon;
import br.com.gabrieudev.emporium.domain.entities.Discount;

public class DiscountInteractor {
    private final DiscountGateway discountGateway;
    private final CouponGateway couponGateway;

    public DiscountInteractor(DiscountGateway discountGateway, CouponGateway couponGateway) {
        this.discountGateway = discountGateway;
        this.couponGateway = couponGateway;
    }

    public Discount create(Discount discount) {
        Coupon coupon = discount.getCoupon();

        BigDecimal total = discount.getOrder().getCart().getTotal();

        if (total.compareTo(coupon.getMinOrderValue()) < 0) {
            throw new InvalidCouponException("Cupom não aplicável.");
        }

        if (coupon.getValidUntil().isBefore(LocalDateTime.now())) {
            throw new InvalidCouponException("Cupom expirado.");
        }

        if (coupon.getUsageCount() >= coupon.getUsageLimit()) {
            throw new InvalidCouponException("Cupom esgotado.");
        }

        coupon.setUsageCount(coupon.getUsageCount() + 1);
        couponGateway.update(coupon);

        return discountGateway.create(discount);
    }

    public void delete(UUID id) {
        Discount discount = discountGateway.findById(id);

        Coupon coupon = discount.getCoupon();

        coupon.setUsageCount(coupon.getUsageCount() - 1);

        couponGateway.update(coupon);

        discountGateway.delete(id);
    }

    public List<Discount> findByOrderId(UUID orderId) {
        return discountGateway.findByOrderId(orderId);
    }
}

package br.com.gabrieudev.emporium.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.emporium.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.application.gateways.CouponGateway;
import br.com.gabrieudev.emporium.application.gateways.DiscountGateway;
import br.com.gabrieudev.emporium.domain.entities.Cart;
import br.com.gabrieudev.emporium.domain.entities.Coupon;
import br.com.gabrieudev.emporium.domain.entities.Discount;

public class DiscountInteractor {
    private final DiscountGateway discountGateway;
    private final CouponGateway couponGateway;
    private final CartGateway cartGateway;

    public DiscountInteractor(DiscountGateway discountGateway, CouponGateway couponGateway, CartGateway cartGateway) {
        this.discountGateway = discountGateway;
        this.couponGateway = couponGateway;
        this.cartGateway = cartGateway;
    }

    public Discount create(Discount discount) {
        Coupon coupon = couponGateway.findById(discount.getCoupon().getId());

        Cart cart = cartGateway.findById(discount.getOrder().getCart().getId());

        if (cart.getTotal().subtract(coupon.getMinOrderValue()).compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleException("Cupom não aplicável.");
        }

        if (coupon.getValidUntil().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("Cupom expirado.");
        }

        if (coupon.getUsageCount() >= coupon.getUsageLimit()) {
            throw new BusinessRuleException("Cupom esgotado.");
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

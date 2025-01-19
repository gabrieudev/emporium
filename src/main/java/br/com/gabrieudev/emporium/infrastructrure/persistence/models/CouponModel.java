package br.com.gabrieudev.emporium.infrastructrure.persistence.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Coupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "coupons")
public class CouponModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;
    
    @Column(name = "percent_off", nullable = false)
    private BigDecimal percentOff;
    
    @Column(name = "min_order_value", nullable = false)
    private BigDecimal minOrderValue;
    
    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;
    
    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;
    
    @Column(name = "usage_limit")
    private Long usageLimit;
    
    @Column(name = "usage_count", nullable = false)
    private Long usageCount;

    @Column(name = "stripe_id", nullable = false)
    private String stripeId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public static CouponModel from(Coupon coupon) {
        return new ModelMapper().map(coupon, CouponModel.class);
    }

    public void update(Coupon coupon) {
        new ModelMapper().map(coupon, this);
    }

    public Coupon toDomainObj() {
        return new ModelMapper().map(this, Coupon.class);
    }

}

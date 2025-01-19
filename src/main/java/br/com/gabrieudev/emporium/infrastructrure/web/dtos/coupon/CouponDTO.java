package br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
    private UUID id;
    private String code;
    private BigDecimal percentOff;
    private BigDecimal minOrderValue;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private Long usageLimit;
    private Long usageCount;
    private String stripeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CouponDTO from(Coupon coupon) {
        return new ModelMapper().map(coupon, CouponDTO.class);
    }

    public Coupon toDomainObj() {
        return new ModelMapper().map(this, Coupon.class);
    }
}

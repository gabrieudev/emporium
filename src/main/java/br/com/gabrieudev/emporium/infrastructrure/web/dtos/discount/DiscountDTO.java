package br.com.gabrieudev.emporium.infrastructrure.web.dtos.discount;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Discount;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon.CouponDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.order.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    private UUID id;
    private OrderDTO order;
    private CouponDTO coupon;

    public static DiscountDTO from(Discount discount) {
        return new ModelMapper().map(discount, DiscountDTO.class);
    }
}

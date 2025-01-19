package br.com.gabrieudev.emporium.infrastructrure.web.dtos.discount;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Discount;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon.CouponDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.order.OrderDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiscountDTO {
    @NotNull(message = "Pedido obrigatório")
    private OrderDTO order;

    @NotNull(message = "Cupom obrigatório")
    private CouponDTO coupon;

    public Discount toDomainObj() {
        return new ModelMapper().map(this, Discount.class);
    }
}

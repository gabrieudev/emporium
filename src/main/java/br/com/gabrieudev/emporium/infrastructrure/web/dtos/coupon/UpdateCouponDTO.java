package br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon;

import java.math.BigDecimal;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCouponDTO {
    @Schema(
        description = "Identificador do cupom",
        example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @Schema(
        description = "Código do cupom",
        example = "ABC123",
        required = true
    )
    @NotNull(message = "Código obrigatório")
    private String code;
    
    @Schema(
        description = "Percentagem de desconto do cupom",
        example = "10",
        required = true
    )
    @NotNull(message = "Desconto obrigatório")
    private BigDecimal percentOff;
    
    @Schema(
        description = "Valor mínimo do pedido acessível ao cupom",
        example = "100",
        required = true
    )
    @NotNull(message = "Valor mínimo do pedido obrigatório")
    private BigDecimal minOrderValue;
    
    @Schema(
        description = "Limite de uso do cupom",
        example = "50",
        required = false
    )
    private Long usageLimit;

    public Coupon toDomainObj() {
        return new ModelMapper().map(this, Coupon.class);
    }
}

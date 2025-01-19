package br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponDTO {
    @Schema(
        description = "Código do cupom",
        example = "ABC123",
        required = true
    )
    @NotBlank(message = "Código obrigatório")
    private String code;

    @Schema(
        description = "Percentagem de desconto do cupom",
        example = "10",
        required = true
    )
    @NotNull(message = "Desconto obrigatório")
    @Positive(message = "Desconto inválido")
    private BigDecimal percentOff;
    
    @Schema(
        description = "Valor mínimo do pedido acessível ao cupom",
        example = "100",
        required = true
    )
    @NotNull(message = "Valor mínimo do pedido obrigatório")
    @Positive(message = "Valor mínimo do pedido inválido")
    private BigDecimal minOrderValue;
    
    @Schema(
        description = "Validade inicial do cupom",
        example = "2023-01-01T00:00:00",
        required = true
    )
    @NotNull(message = "Validade inicial do cupom obrigatória")
    @FutureOrPresent(message = "Validade inicial do cupom inválida")
    private LocalDateTime validFrom;
    
    @Schema(
        description = "Validade final do cupom",
        example = "2023-01-01T00:00:00",
        required = true
    )
    @NotNull(message = "Validade final do cupom obrigatória")
    @Future(message = "Validade final do cupom inválida")
    private LocalDateTime validUntil;
    
    @Schema(
        description = "Limite de uso do cupom",
        example = "100",
        required = false
    )
    private Long usageLimit;
    
    public Coupon toDomainObj() {
        return new ModelMapper().map(this, Coupon.class);
    }
}

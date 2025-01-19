package br.com.gabrieudev.emporium.infrastructrure.web.dtos.cartitem;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemDTO {
    @Schema(
        description = "Identificador do produto",
        example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @Schema(
        description = "Quantidade de produtos",
        example = "3",
        required = true
    )
    @NotNull(message = "Quantidade obrigatória")
    private Integer quantity;

    public CartItem toDomainObj() {
        return new ModelMapper().map(this, CartItem.class);
    }
}

package br.com.gabrieudev.emporium.infrastructrure.web.dtos.stock;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Stock;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class UpdateStockDTO {
    @Schema(
        description = "Identificador do estoque",
        example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @Schema(
        description = "Quantidade do produto no estoque",
        example = "100",
        required = true
    )
    @NotNull(message = "Quantidade obrigatória")
    private Long quantity;

    public Stock toDomainObj() {
        return new ModelMapper().map(this, Stock.class);
    }
}

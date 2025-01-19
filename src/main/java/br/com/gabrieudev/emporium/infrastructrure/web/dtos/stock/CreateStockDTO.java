package br.com.gabrieudev.emporium.infrastructrure.web.dtos.stock;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Stock;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.product.ProductDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStockDTO {
    @NotNull(message = "Produto obrigatório")
    private ProductDTO product;
    
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

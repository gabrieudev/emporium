package br.com.gabrieudev.emporium.infrastructrure.web.dtos.product;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Product;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.category.CategoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    @Schema(
        description = "Nome do produto",
        example = "Camisa",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String name;

    @Schema(
        description = "Descrição do produto",
        example = "Camisa branca",
        required = false
    )
    private String description;
    
    @Schema(
        description = "Preço do produto",
        example = "19.99",
        required = true
    )
    @NotNull(message = "Preço obrigatório")
    private BigDecimal price;
    
    @Schema(
        description = "URL da imagem do produto",
        example = "https://example.com/image.jpg",
        required = false
    )
    private String image;

    @NotNull(message = "Categoria obrigatória")
    private CategoryDTO category;

    public Product toDomainObj() {
        return new ModelMapper().map(this, Product.class);
    }
}

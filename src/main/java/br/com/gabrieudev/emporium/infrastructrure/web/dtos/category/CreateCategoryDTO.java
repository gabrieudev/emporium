package br.com.gabrieudev.emporium.infrastructrure.web.dtos.category;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryDTO {
    @Schema(
        description = "Nome da categoria",
        example = "Roupas",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String name;
    
    @Schema(
        description = "Descrição da categoria",
        example = "Roupas de vestir",
        required = false
    )
    @NotBlank(message = "Descrição obrigatória")
    private String description;

    public Category toDomainObj() {
        return new ModelMapper().map(this, Category.class);
    }
}

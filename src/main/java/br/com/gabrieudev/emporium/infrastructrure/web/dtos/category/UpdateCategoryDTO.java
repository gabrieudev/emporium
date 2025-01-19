package br.com.gabrieudev.emporium.infrastructrure.web.dtos.category;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDTO {
    @Schema(
        description = "Identificador da categoria",
        example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;
    
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

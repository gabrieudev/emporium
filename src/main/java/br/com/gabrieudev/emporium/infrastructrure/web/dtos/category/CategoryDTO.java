package br.com.gabrieudev.emporium.infrastructrure.web.dtos.category;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CategoryDTO from(Category category) {
        return new ModelMapper().map(category, CategoryDTO.class);
    }

    public Category toDomainObj() {
        return new ModelMapper().map(this, Category.class);
    }
}

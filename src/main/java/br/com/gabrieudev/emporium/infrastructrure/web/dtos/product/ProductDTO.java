package br.com.gabrieudev.emporium.infrastructrure.web.dtos.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Product;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private String stripeId;
    private CategoryDTO category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductDTO from(Product product) {
        return new ModelMapper().map(product, ProductDTO.class);
    }

    public Product toDomainObj() {
        return new ModelMapper().map(this, Product.class);
    }
}

package br.com.gabrieudev.emporium.infrastructrure.web.dtos.stock;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Stock;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private UUID id;
    private ProductDTO product;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StockDTO from(Stock stock) {
        return new ModelMapper().map(stock, StockDTO.class);
    }

    public Stock toDomainObj() {
        return new ModelMapper().map(this, Stock.class);
    }
}

package br.com.gabrieudev.emporium.infrastructrure.web.dtos.cartitem;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.CartItem;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.cart.CartDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.product.ProductDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartItemDTO {
    @NotNull(message = "Carrinho obrigatório")
    private CartDTO cart;

    @NotNull(message = "Produto obrigatório")
    private ProductDTO product;
    
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

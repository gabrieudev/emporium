package br.com.gabrieudev.emporium.infrastructrure.web.dtos.cartitem;

import java.math.BigDecimal;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.CartItem;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.cart.CartDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private UUID id;
    private CartDTO cart;
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal total;
    private Boolean isActive;

    public static CartItemDTO from(CartItem orderItem) {
        return new ModelMapper().map(orderItem, CartItemDTO.class);
    }

    public CartItem toDomainObj() {
        return new ModelMapper().map(this, CartItem.class);
    }
}

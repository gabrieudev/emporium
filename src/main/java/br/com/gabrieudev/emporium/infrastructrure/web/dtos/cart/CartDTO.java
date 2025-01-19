package br.com.gabrieudev.emporium.infrastructrure.web.dtos.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Cart;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private UUID id;
    private UserDTO user;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CartDTO from(Cart cart) {
        return new ModelMapper().map(cart, CartDTO.class);
    }

    public Cart toDomainObj() {
        return new ModelMapper().map(this, Cart.class);
    }
}

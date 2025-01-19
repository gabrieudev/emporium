package br.com.gabrieudev.emporium.infrastructrure.web.dtos.order;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Order;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.cart.CartDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {
    @NotNull(message = "Carrinho obrigatório")
    private CartDTO cart;

    public Order toDomainObj() {
        return new ModelMapper().map(this, Order.class);
    }
}

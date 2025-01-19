package br.com.gabrieudev.emporium.infrastructrure.web.dtos.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Order;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.cart.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private CartDTO cart;
    private String status;
    private BigDecimal discount;
    private BigDecimal total;
    private String city;
    private String country;
    private String postalCode;
    private String state;
    private String line1;
    private String line2;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderDTO from(Order order) {
        return new ModelMapper().map(order, OrderDTO.class);
    }

    public Order toDomainObj() {
        return new ModelMapper().map(this, Order.class);
    }
}

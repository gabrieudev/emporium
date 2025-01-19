package br.com.gabrieudev.emporium.infrastructrure.persistence.models;

import java.math.BigDecimal;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.CartItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "cart_items")
public class CartItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartModel cart;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public static CartItemModel from(CartItem cartItem) {
        return new ModelMapper().map(cartItem, CartItemModel.class);
    }

    public void update(CartItem cartItem) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(cartItem, this);
    }

    public CartItem toDomainObj() {
        return new ModelMapper().map(this, CartItem.class);
    }
}

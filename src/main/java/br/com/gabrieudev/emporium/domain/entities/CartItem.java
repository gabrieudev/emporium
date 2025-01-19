package br.com.gabrieudev.emporium.domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItem {
    private UUID id;
    private Cart cart;
    private Product product;
    private Integer quantity;
    private BigDecimal total;
    private Boolean isActive;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public CartItem(UUID id, Cart cart, Product product, Integer quantity, BigDecimal total, Boolean isActive) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
        this.isActive = isActive;
    }
    
    public CartItem() {
    }
}

package br.com.gabrieudev.emporium.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private UUID id;
    private Cart cart;
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
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getDiscount() {
        return discount;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getLine1() {
        return line1;
    }
    public void setLine1(String line1) {
        this.line1 = line1;
    }
    public String getLine2() {
        return line2;
    }
    public void setLine2(String line2) {
        this.line2 = line2;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    public Order(UUID id, Cart cart, String status, BigDecimal discount, BigDecimal total, String city, String country,
            String postalCode, String state, String line1, String line2, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.cart = cart;
        this.status = status;
        this.discount = discount;
        this.total = total;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.state = state;
        this.line1 = line1;
        this.line2 = line2;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public Order() {
    }
    
}

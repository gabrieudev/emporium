package br.com.gabrieudev.emporium.domain.entities;

import java.util.UUID;

public class Discount {
    private UUID id;
    private Order order;
    private Coupon coupon;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Coupon getCoupon() {
        return coupon;
    }
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Discount(UUID id, Order order, Coupon coupon) {
        this.id = id;
        this.order = order;
        this.coupon = coupon;
    }
    
    public Discount() {
    }
    
}

package br.com.gabrieudev.emporium.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.stripe.model.checkout.Session;

import br.com.gabrieudev.emporium.application.gateways.OrderGateway;
import br.com.gabrieudev.emporium.domain.entities.Order;

public class OrderInteractor {
    private final OrderGateway orderGateway;

    public OrderInteractor(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Order create(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return orderGateway.create(order);
    }

    public void updatePayedOrder(Session session) {
        orderGateway.updatePayedOrder(session);
    }

    public String generatePaymentLink(UUID orderId) {
        return orderGateway.generatePaymentLink(orderId);
    }

    public Order findById(UUID id) {
        return orderGateway.findById(id);
    }

    public List<Order> findAll(Integer page, Integer size) {
        return orderGateway.findAll(page, size);
    }

    public List<Order> findByUserId(UUID userId, Integer page, Integer size) {
        return orderGateway.findByUserId(userId, page, size);
    }

}

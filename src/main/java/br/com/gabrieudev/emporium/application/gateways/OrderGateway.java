package br.com.gabrieudev.emporium.application.gateways;

import java.util.List;
import java.util.UUID;

import com.stripe.model.checkout.Session;

import br.com.gabrieudev.emporium.domain.entities.Order;

public interface OrderGateway {
    Order create(Order order);
    String generatePaymentLink(UUID orderId);
    void updatePayedOrder(Session session);
    Order findById(UUID id);
    boolean existsById(UUID id);
    List<Order> findAll(Integer page, Integer size);
    List<Order> findByUserId(UUID userId, Integer page, Integer size);
}

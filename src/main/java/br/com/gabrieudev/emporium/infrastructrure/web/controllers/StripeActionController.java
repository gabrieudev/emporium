package br.com.gabrieudev.emporium.infrastructrure.web.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import br.com.gabrieudev.emporium.application.exceptions.StandardException;
import br.com.gabrieudev.emporium.application.exceptions.TransactionFailedException;
import br.com.gabrieudev.emporium.application.usecases.CartInteractor;
import br.com.gabrieudev.emporium.application.usecases.OrderInteractor;
import br.com.gabrieudev.emporium.domain.entities.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/stripe")
public class StripeActionController {
    private final OrderInteractor orderInteractor;
    private final CartInteractor cartInteractor;
    @Value("${stripe.secret}")
    private String secret;

    public StripeActionController(OrderInteractor orderInteractor, CartInteractor cartInteractor) {
        this.orderInteractor = orderInteractor;
        this.cartInteractor = cartInteractor;
    }

    @Operation(
        summary = "Webhook da Stripe",
        description = "Recebe o webhook da Stripe após a finalização do pagamento e atualiza informações do pedido",
        tags = "Stripe"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Webhook processado com sucesso"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Erro ao processar o webhook",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(
        @RequestBody String payload,
        @RequestHeader("Stripe-Signature") String signature
    ) {
        Event event;
        
        try {
            event = Webhook.constructEvent(payload, signature, secret);
        } catch (Exception e) {
            throw new TransactionFailedException("Erro ao processar o webhook: " + e.getMessage());
        }

        if (!event.getType().equals("checkout.session.completed")) {
            throw new TransactionFailedException("Evento inválido: " + event.getType());
        }

        EventDataObjectDeserializer data = event.getDataObjectDeserializer();

        Session session = (Session) data.getObject().get();

        orderInteractor.updatePayedOrder(session);

        String orderId = session.getMetadata().get("orderId");

        Order order = orderInteractor.findById(UUID.fromString(orderId));

        cartInteractor.clear(order.getCart());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

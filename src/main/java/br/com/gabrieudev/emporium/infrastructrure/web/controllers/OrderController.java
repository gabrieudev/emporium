package br.com.gabrieudev.emporium.infrastructrure.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.emporium.application.exceptions.StandardException;
import br.com.gabrieudev.emporium.application.usecases.OrderInteractor;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.PaymentLinkReponse;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.order.CreateOrderDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.order.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {
    private final OrderInteractor orderInteractor;

    public OrderController(OrderInteractor orderInteractor) {
        this.orderInteractor = orderInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Realizar um pedido",
        description = "Realiza um pedido",
        tags = "Orders",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Pedido realizado com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Informações inválidas",
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
    @PostMapping
    public ResponseEntity<OrderDTO> create(
        @Valid
        @RequestBody
        CreateOrderDTO createOrderDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDTO.from(orderInteractor.create(createOrderDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter pedido",
        description = "Obtém um pedido de acordo com o ID do pedido no parâmetro UUID",
        tags = "Orders",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Pedido obtido com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso invático ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Pedido não encontrado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
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
            )
        }
    )
    @GetMapping("/{UUID}")
    public ResponseEntity<OrderDTO> findById(@PathVariable UUID UUID) {
        return ResponseEntity.status(HttpStatus.OK).body(OrderDTO.from(orderInteractor.findById(UUID)));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter pedidos",
        description = "Obtém todos os pedidos",
        tags = "Orders",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Pedidos obtidos com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
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
            )
        }
    )
    @GetMapping
    public ResponseEntity<Page<OrderDTO>> findByUser(
        @Parameter(
            name = "page",
            description = "Página atual da paginação"
        )
        @RequestParam(required = true) Integer page,
        
        @Parameter(
            name = "size",
            description = "Quantidade de itens por página"
        )
        @RequestParam(required = true) Integer size,

        @Parameter(
            description = "Identificador do usuário",
            example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a"
        )
        @RequestParam(required = true) UUID userId
    ) {
        List<OrderDTO> orders = orderInteractor.findByUserId(userId, page, size)
            .stream()
            .map(OrderDTO::from)
            .toList();

        Page<OrderDTO> orderPage = new PageImpl<>(orders, PageRequest.of(page, size), orders.size());

        return ResponseEntity.status(HttpStatus.OK).body(orderPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter link de pagamento",
        description = "Obtém um link de pagamento de acordo com o ID do pedido no parâmetro UUID",
        tags = "Orders",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Link de pagamento obtido"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Pedido não encontrado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Erro ao gerar link de pagamento",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
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
    @GetMapping("/{UUID}/payment-link")
    public ResponseEntity<PaymentLinkReponse> getPaymentLink(
        @Parameter(
            description = "Identificador do pedido",
            example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a"
        )
        @PathVariable UUID UUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(new PaymentLinkReponse(orderInteractor.generatePaymentLink(UUID)));
    }
}

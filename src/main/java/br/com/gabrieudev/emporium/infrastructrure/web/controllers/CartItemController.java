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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.emporium.application.exceptions.StandardException;
import br.com.gabrieudev.emporium.application.usecases.CartItemInteractor;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.cartitem.CartItemDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.cartitem.CreateCartItemDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.cartitem.UpdateCartItemDTO;
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
@RequestMapping("/cart-items")
public class CartItemController {
    private final CartItemInteractor cartItemInteractor;

    public CartItemController(CartItemInteractor cartItemInteractor) {
        this.cartItemInteractor = cartItemInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Criar item de carrinho",
        description = "Cria um item de carrinho de acordo com o corpo da requisição",
        tags = "CartItems",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Item de carrinho criado"
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
                responseCode = "409",
                description = "Estoque insuficiente",
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
    public ResponseEntity<CartItemDTO> create(
        @Valid
        @RequestBody
        CreateCartItemDTO createCartItemDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CartItemDTO.from(cartItemInteractor.create(createCartItemDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Atualizar item de carrinho",
        description = "Atualiza um item de carrinho de acordo com o corpo da requisição",
        tags = "CartItems",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Item de carrinho atualizado"
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
                description = "Item de carrinho não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
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
    @PutMapping
    public ResponseEntity<CartItemDTO> update(
        @Valid 
        @RequestBody
        UpdateCartItemDTO updateCartItemDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CartItemDTO.from(cartItemInteractor.update(updateCartItemDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter item de carrinho",
        description = "Obtém um item de carrinho de acordo com o ID no parâmetro UUID",
        tags = "CartItems",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Item de carrinho obtido"
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
                description = "Item de carrinho não encontrado",
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
    @GetMapping("/{UUID}")
    public ResponseEntity<CartItemDTO> findById(
        @Parameter(
            description = "Identificador do item de carrinho",
            example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a"
        )
        @PathVariable UUID UUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CartItemDTO.from(cartItemInteractor.findById(UUID)));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter por pedido",
        description = "Obtém itens de carrinho de acordo com o ID do carrinho no parâmetro cartId",
        tags = "CartItems",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Itens de carrinho obtidos"
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
                description = "Carrinho não encontrado",
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
    @GetMapping
    public ResponseEntity<Page<CartItemDTO>> findByOrder(
        @Parameter(
            name = "page",
            description = "Página atual da paginação"
        )
        @RequestParam(required = false) Integer page,
        
        @Parameter(
            name = "size",
            description = "Quantidade de itens por página"
        )
        @RequestParam(required = false) Integer size,

        @Parameter(
            description = "Identificador do carrinho",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @RequestParam(required = true) UUID cartId
    ) {
        List<CartItemDTO> cartItems = cartItemInteractor.findByCartId(cartId, page, size).stream()
                .map(CartItemDTO::from)
                .toList();

        Page<CartItemDTO> cartItemsPage = new PageImpl<>(cartItems, PageRequest.of(page, size), cartItems.size());

        return ResponseEntity.status(HttpStatus.OK).body(cartItemsPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Deletar item de carrinho",
        description = "Deleta um item de carrinho de acordo com o ID no parâmetro UUID",
        tags = "CartItems",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Item de carrinho deletado"
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
                description = "Item de carrinho não encontrado",
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
    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> delete(
        @Parameter(
            description = "Identificador do item de carrinho",
            example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a"
        )
        @PathVariable UUID UUID
    ) {
        cartItemInteractor.delete(UUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package br.com.gabrieudev.emporium.infrastructrure.web.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.emporium.application.exceptions.StandardException;
import br.com.gabrieudev.emporium.application.usecases.DiscountInteractor;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.discount.CreateDiscountDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.discount.DiscountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountInteractor discountInteractor;

    public DiscountController(DiscountInteractor discountInteractor) {
        this.discountInteractor = discountInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Criar desconto",
        description = "Cria um desconto",
        tags = "Discounts",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Desconto criado com sucesso"
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
                description = "Erro de validação no corpo da requisição",
                content = @Content(
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
    public ResponseEntity<DiscountDTO> create(
        @Valid
        @RequestBody
        CreateDiscountDTO createDiscountDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(DiscountDTO.from(discountInteractor.create(createDiscountDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Deletar desconto",
        description = "Deleta um desconto",
        tags = "Discounts",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Desconto deletado com sucesso"
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
                description = "Desconto não encontrado",
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
    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> delete(
        @PathVariable UUID UUID
    ) {
        discountInteractor.delete(UUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

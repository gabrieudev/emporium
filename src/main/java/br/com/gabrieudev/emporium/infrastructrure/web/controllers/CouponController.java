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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.emporium.application.exceptions.StandardException;
import br.com.gabrieudev.emporium.application.usecases.CouponInteractor;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon.CouponDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon.CreateCouponDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.coupon.UpdateCouponDTO;
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
@RequestMapping("/coupons")
public class CouponController {
    private final CouponInteractor couponInteractor;

    public CouponController(CouponInteractor couponInteractor) {
        this.couponInteractor = couponInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Criar cupom",
        description = "Cria um cupom de desconto",
        tags = "Coupons",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Cupom criado com sucesso"
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
                responseCode = "409",
                description = "Cupom com o mesmo código já cadastrado",
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
    public ResponseEntity<CouponDTO> createCoupon(
        @Valid
        @RequestBody
        CreateCouponDTO createCouponDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CouponDTO.from(couponInteractor.create(createCouponDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Obter cupom",
        description = "Obtém um cupom de desconto de acordo com o ID no parâmetro UUID",
        tags = "Coupons",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Cupom obtido com sucesso"
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
                description = "Cupom não encontrado",
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
    @GetMapping("/{UUID}")
    public ResponseEntity<CouponDTO> getCoupon(
        @Parameter(
            description = "Identificador do cupom",
            example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a"
        )
        @PathVariable UUID UUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CouponDTO.from(couponInteractor.findById(UUID)));
    }

    @PreAuthorize(
            "hasAuthority('SCOPE_ADMIN') or " +
            "(hasAuthority('SCOPE_USER') and #code != null and #page == null and #size == null)"
    )
    @Operation(
        summary = "Obter cupons",
        description = "Obtém todos os cupons de descontos",
        tags = "Coupons",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Cupons obtidos"
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
                description = "Cupom não encontrado",
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
    @GetMapping
    public ResponseEntity<?> getCoupons(
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
            name = "code",
            description = "Código do cupom"
        )
        @RequestParam(required = false) String code
    ) {
        if (code != null) {
            return ResponseEntity.status(HttpStatus.OK).body(CouponDTO.from(couponInteractor.findByCode(code)));
        }

        List<CouponDTO> coupons = couponInteractor.findAll(page, size).stream()
            .map(CouponDTO::from)
            .toList();

        Page<CouponDTO> pageCoupons = new PageImpl<>(coupons, PageRequest.of(page, size), coupons.size());

        return ResponseEntity.status(HttpStatus.OK).body(pageCoupons);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Atualizar cupom",
        description = "Atualiza um cupom de desconto",
        tags = "Coupons",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Cupom atualizado com sucesso"
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
                responseCode = "409",
                description = "Cupom com o mesmo código já cadastrado",
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
    @PutMapping
    public ResponseEntity<CouponDTO> updateCoupon(
        @Valid
        @RequestBody
        UpdateCouponDTO updateCouponDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CouponDTO.from(couponInteractor.update(updateCouponDTO.toDomainObj())));
    }
}

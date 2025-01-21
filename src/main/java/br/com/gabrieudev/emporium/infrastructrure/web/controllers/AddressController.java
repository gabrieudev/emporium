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
import br.com.gabrieudev.emporium.application.usecases.AddressInteractor;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.address.AddressDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.address.CreateAddressDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.address.UpdateAddressDTO;
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
@RequestMapping("/addresses")
public class AddressController {
    private final AddressInteractor addressInteractor;

    public AddressController(AddressInteractor addressInteractor) {
        this.addressInteractor = addressInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Criar endereço",
        description = "Cria um endereço de acordo com o corpo da requisição",
        tags = "Addresses",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Endereço criado com sucesso"
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Erro de validação no corpo da requisição",
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
    public ResponseEntity<AddressDTO> create(
        @Valid
        @RequestBody
        CreateAddressDTO createAddressDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AddressDTO.from(addressInteractor.create(createAddressDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Atualizar endereço",
        description = "Atualiza um endereço de acordo com o corpo da requisição",
        tags = "Addresses",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Endereço atualizado com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Endereço nao encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Erro de validação no corpo da requisição",
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
    public ResponseEntity<AddressDTO> update(
        @Valid
        @RequestBody
        UpdateAddressDTO updateAddressDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(AddressDTO.from(addressInteractor.update(updateAddressDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter endereço",
        description = "Obtém um endereço de acordo com o parâmetro da requisição",
        tags = "Addresses",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Endereço obtido com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Endereço não encontrado",
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
    public ResponseEntity<AddressDTO> getById(
        @PathVariable UUID UUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(AddressDTO.from(addressInteractor.findById(UUID)));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter endereços",
        description = "Obtém endereços de acordo com o parâmetro da requisição",
        tags = "Addresses",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Endereços obtidos com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Endereços não encontrados",
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
    public ResponseEntity<Page<AddressDTO>> getAddresses(
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
            name = "userId",
            description = "Identificador do usuário"
        )
        @RequestParam(required = false) UUID userId
    ) {
        List<AddressDTO> addresses = addressInteractor.findByUserId(userId).stream()
                .map(AddressDTO::from)
                .toList();

        Page<AddressDTO> addressesPage = new PageImpl<>(addresses, PageRequest.of(page, size), addresses.size());

        return ResponseEntity.status(HttpStatus.OK).body(addressesPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Deletar endereço",
        description = "Deleta um endereço de acordo com o parâmetro da requisição",
        tags = "Addresses",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Endereço deletado com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Endereço nao encontrado",
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
        @PathVariable UUID UUID
    ) {
        addressInteractor.delete(UUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

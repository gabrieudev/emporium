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
import br.com.gabrieudev.emporium.application.usecases.CategoryInteractor;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.category.CategoryDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.category.CreateCategoryDTO;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.category.UpdateCategoryDTO;
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
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryInteractor categoryInteractor;

    public CategoryController(CategoryInteractor categoryInteractor) {
        this.categoryInteractor = categoryInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Criar categoria",
        description = "Cria uma nova categoria",
        tags = "Categories",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Categoria criada com sucesso"
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
    public ResponseEntity<CategoryDTO> create(
        @Valid
        @RequestBody
        CreateCategoryDTO createCategoryDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryDTO.from(categoryInteractor.create(createCategoryDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Atualizar categoria",
        description = "Atualiza uma categoria de acordo com o corpo da requisição",
        tags = "Categories",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Categoria atualizada com sucesso"
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
                description = "Categoria não encontrada",
                content = @Content(
                    schema = @Schema(
                        implementation = StandardException.class
                    )
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
    @PutMapping
    public ResponseEntity<CategoryDTO> update(
        @Valid
        @RequestBody
        UpdateCategoryDTO updateCategoryDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CategoryDTO.from(categoryInteractor.update(updateCategoryDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Deletar categoria",
        description = "Deleta uma categoria",
        tags = "Categories",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Categoria deletada com sucesso"
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
                description = "Categoria não encontrada",
                content = @Content(
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Categoria possui produtos cadastrados",
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
    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> delete(
        @Parameter(
            description = "Identificador da categoria",
            example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a"
        )
        @PathVariable UUID UUID
    ) {
        categoryInteractor.delete(UUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter categorias",
        description = "Obtem todas as categorias",
        tags = "Categories",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Categorias obtidas com sucesso"
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
            ),
        }
    )
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAll(
        @Parameter(
            name = "page",
            description = "Página atual da paginação"
        )
        @RequestParam(required = false) Integer page,
        
        @Parameter(
            name = "size",
            description = "Quantidade de itens por página"
        )
        @RequestParam(required = false) Integer size
    ) {
        List<CategoryDTO> categories = categoryInteractor.findAll(page, size).stream().map(CategoryDTO::from).toList();

        Page<CategoryDTO> categoriesPage = new PageImpl<>(categories, PageRequest.of(page, size), categories.size());

        return ResponseEntity.status(HttpStatus.OK).body(categoriesPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter categoria",
        description = "Obtem uma categoria",
        tags = "Categories",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Categoria obtida com sucesso"
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
                description = "Categoria não encontrada",
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
    public ResponseEntity<CategoryDTO> findCategory(
        @Parameter(
            description = "Identificador da categoria",
            example = "7f4e4b24-1b0a-4a1a-9c1a-1b0a4a1a4a1a"
        )
        @PathVariable UUID UUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CategoryDTO.from(categoryInteractor.findById(UUID)));
    }
}

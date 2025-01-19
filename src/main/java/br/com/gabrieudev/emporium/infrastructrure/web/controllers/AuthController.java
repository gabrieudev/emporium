package br.com.gabrieudev.emporium.infrastructrure.web.controllers;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.emporium.application.exceptions.StandardException;
import br.com.gabrieudev.emporium.application.usecases.AuthInteractor;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.auth.LoginRequest;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.auth.LoginResponse;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.auth.RefreshTokenRequest;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.auth.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthInteractor authInteractor;
    
    public AuthController(AuthInteractor authInteractor) {
        this.authInteractor = authInteractor;
    }

    @Operation(
        summary = "Realizar login",
        description = "Realiza o login do usuário e retorna o token de acesso e o token de atualização",
        tags = "Auth"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário logado com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Credenciais inválidas",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Credenciais inválidas",
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
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(
        @Valid
        @RequestBody 
        LoginRequest loginRequest
    ) {
        Map<String, String> tokens = authInteractor.signin(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(tokens.get("token"), tokens.get("refreshToken"), Instant.now().plusSeconds(600), Instant.now().plusSeconds(1296000)));
    }

    @Operation(
        summary = "Atualizar token de acesso",
        description = "Atualiza o token de acesso do usuário e retorna o token de acesso e o token de atualização",
        tags = "Auth"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Token atualizado com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
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
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
        @Valid
        @RequestBody 
        RefreshTokenRequest refreshTokenRequest
    ) {
        String token = authInteractor.refresh(refreshTokenRequest.getRefreshToken());
        
        return ResponseEntity.status(HttpStatus.OK).body(new 
        RefreshTokenResponse(token));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Invalidar tokens",
        description = "Invalida o token de acesso e o token de atualização do usuário",
        tags = "Auth",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tokens invalidados com sucesso"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token inválido ou expirado",
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
    @PostMapping("/signout")
    public ResponseEntity<Void> signout(
        @Valid
        @RequestBody 
        RefreshTokenRequest refreshTokenRequest,
        HttpServletRequest request
    ) {
        String accessToken = request.getHeader("Authorization").split(" ")[1];
        authInteractor.logout(refreshTokenRequest.getRefreshToken(), accessToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

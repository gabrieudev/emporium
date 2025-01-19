package br.com.gabrieudev.emporium.infrastructrure.web.dtos.auth;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Instant accessTokenExpiration;
    private Instant refreshTokenExpiration;
}

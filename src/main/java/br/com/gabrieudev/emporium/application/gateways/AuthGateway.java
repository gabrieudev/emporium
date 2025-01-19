package br.com.gabrieudev.emporium.application.gateways;

import br.com.gabrieudev.emporium.domain.entities.User;

public interface AuthGateway {
    String generateAccessToken(User user);
    String refresh (String refreshToken);
    String generateRefreshToken(User user);
    boolean isValid(String token);
    void logout(String refreshToken, String accessToken);
}

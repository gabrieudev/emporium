package br.com.gabrieudev.emporium.infrastructrure.config.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.gabrieudev.emporium.infrastructrure.gateways.AuthServiceGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    private final AuthServiceGateway authServiceGateway;

    public TokenValidationFilter(AuthServiceGateway authServiceGateway) {
        this.authServiceGateway = authServiceGateway;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader("Authorization") != null) {
            String token = request.getHeader("Authorization").split(" ")[1];

            if (!authServiceGateway.isValid(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invático");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

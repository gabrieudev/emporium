package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.emporium.application.gateways.AuthGateway;
import br.com.gabrieudev.emporium.domain.entities.User;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UsersRolesModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UsersRolesRepository;

@Service
public class AuthServiceGateway implements AuthGateway {
    @Value("${spring.application.name}")
    private String issuer;

    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;
    private final UsersRolesRepository usersRolesRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public AuthServiceGateway(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder, UsersRolesRepository usersRolesRepository,
            UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.jwtDecoder = jwtDecoder;
        this.jwtEncoder = jwtEncoder;
        this.usersRolesRepository = usersRolesRepository;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public String generateAccessToken(User user) {
        List<String> roles = usersRolesRepository.findByUser(UserModel.fromDomainObj(user)).stream()
                .map(UsersRolesModel::getRole)
                .map(RoleModel::getName)
                .toList();

        String scopes = String.join(" ", roles);
        var claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuer(issuer)
                .expiresAt(Instant.now().plusSeconds(600))
                .issuedAt(Instant.now())
                .claim("FirstName", user.getFirstName())
                .claim("LastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("scope", scopes)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        String userId = user.getId().toString();

        redisTemplate.opsForValue().set("accessToken:" + userId + ":" + accessToken, accessToken, Duration.ofSeconds(600));

        return accessToken;
    }

    @Override
    @Transactional
    public String generateRefreshToken(User user) {
        var claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuer(issuer)
                .expiresAt(Instant.now().plusSeconds(1296000))
                .issuedAt(Instant.now())
                .build();

        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        redisTemplate.delete("refreshToken:" + user.getId().toString());

        redisTemplate.opsForValue().set("refreshToken:" + user.getId().toString(), refreshToken, Duration.ofSeconds(1296000));

        return refreshToken;
    }

    @Override
    public String refresh(String refreshToken) {
        try {
            var jwt = jwtDecoder.decode(refreshToken);
            UserModel user = userRepository.findById(UUID.fromString(jwt.getSubject()))
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

            String token = generateAccessToken(user.toDomainObj());

            return token;
        } catch (Exception e) {
            throw new InvalidTokenException("Refresh token inválido");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();

            return redisTemplate.hasKey("refreshToken:" + userId) && redisTemplate.hasKey("accessToken:" + userId + ":" + jwt.getTokenValue());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void logout(String refreshToken, String accessToken) {
        try {
            var refreshjwt = jwtDecoder.decode(refreshToken);
            var accessjwt = jwtDecoder.decode(accessToken);

            String userId = refreshjwt.getSubject();

            redisTemplate.delete("refreshToken:" + userId);
            redisTemplate.delete("accessToken:" + userId + ":" + accessjwt.getTokenValue());
        } catch (Exception e) {
            throw new InvalidTokenException("Token inválido");
        }
    }

}

package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.emporium.application.gateways.UserGateway;
import br.com.gabrieudev.emporium.domain.entities.User;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;

@Service
public class UserServiceGateway implements UserGateway {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;

    public UserServiceGateway(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtDecoder jwtDecoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    @CacheEvict(value = "Users", key = "#id")
    @Transactional
    public void delete(UUID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(UserModel::toDomainObj)    
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Override
    @Cacheable(value = "Users", key = "#id")
    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository.findById(id)
            .map(UserModel::toDomainObj)    
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> search(String param, Integer page, Integer size) {
        return userRepository.search(param, PageRequest.of(page, size))
            .stream()
            .map(UserModel::toDomainObj)
            .toList();
    }

    @Override
    @CacheEvict(value = "Users", key = "#user.id")
    @Transactional
    public User signup(User user) {
        return userRepository.save(UserModel.fromDomainObj(user)).toDomainObj();
    }

    @Override
    @CacheEvict(value = "Users", key = "#user.id")
    @Transactional
    public User update(User user) {
        UserModel userToUpdate = userRepository.findById(user.getId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        userToUpdate.update(user);

        return userRepository.save(userToUpdate).toDomainObj();
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    @Override
    public User findByToken(String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();

            return findById(UUID.fromString(userId));
        } catch (Exception e) {
            throw new InvalidTokenException("Token inválido");
        }
    }

}

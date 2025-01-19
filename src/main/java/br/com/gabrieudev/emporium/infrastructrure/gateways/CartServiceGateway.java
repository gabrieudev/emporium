package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.UUID;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.emporium.application.gateways.CartGateway;
import br.com.gabrieudev.emporium.domain.entities.Cart;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartItemRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.UserRepository;

public class CartServiceGateway implements CartGateway {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;

    public CartServiceGateway(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository, JwtDecoder jwtDecoder) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Cart findByToken(String token) {
        String userId = null;

        try {
            var jwt = jwtDecoder.decode(token);
            userId = jwt.getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException("Token inválido.");
        }

        UserModel user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        return cartRepository.findByUser(user)
                .map(CartModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado."));
    }

    @Override
    @Transactional
    public Cart create(Cart cart) {
        return cartRepository.save(CartModel.from(cart)).toDomainObj();
    }

    @Override
    @Transactional
    public Cart update(Cart cart) {
        CartModel cartModel = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado."));
        
        cartModel.update(cart);

        return cartRepository.save(cartModel).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public Cart findById(UUID id) {
        return cartRepository.findById(id)
                .map(CartModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado."));
    }

}

package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.gateways.CartItemGateway;
import br.com.gabrieudev.emporium.domain.entities.CartItem;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartItemModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.CartModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartItemRepository;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.CartRepository;

public class CartItemServiceGateway implements CartItemGateway {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartItemServiceGateway(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return cartItemRepository.existsById(id);
    }

    @Override
    @CacheEvict(value = "CartItems", key = "#carttItem.id")
    @Transactional
    public CartItem create(CartItem carttItem) {
        return cartItemRepository.save(CartItemModel.from(carttItem)).toDomainObj();
    }

    @Override
    @CacheEvict(value = "OrderItems", key = "#id")
    @Transactional
    public void delete(UUID id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "CartItems", key = "#id")
    @Transactional(readOnly = true)
    public CartItem findById(UUID id) {
        return cartItemRepository.findById(id)
                .map(CartItemModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Item de carrinho não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> findByCartId(UUID cartId, Integer page, Integer size) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));

        return cartItemRepository.findByCartAndIsActiveTrue(cart, PageRequest.of(page, size)).stream()
                .map(CartItemModel::toDomainObj)
                .toList();
    }

    @Override
    @CacheEvict(value = "CartItems", key = "#carttItem.id")
    @Transactional
    public CartItem update(CartItem cartItem) {
        CartItem cartItemToUpdate = findById(cartItem.getId());

        CartItemModel cartItemModel = CartItemModel.from(cartItemToUpdate);
        cartItemModel.update(cartItem);

        return cartItemRepository.save(cartItemModel).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> findByCartId(UUID cartId) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));

        return cartItemRepository.findByCartAndIsActiveTrue(cart).stream()
                .map(CartItemModel::toDomainObj)
                .toList();
    }

}

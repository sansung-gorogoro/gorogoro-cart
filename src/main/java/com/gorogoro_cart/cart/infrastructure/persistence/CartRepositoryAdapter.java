package com.gorogoro_cart.cart.infrastructure.persistence;

import com.gorogoro_cart.cart.domain.model.entity.Cart;
import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import com.gorogoro_cart.cart.infrastructure.persistence.entity.CartItemJpaEntity;
import com.gorogoro_cart.cart.infrastructure.persistence.jpa.CartItemJpaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {
    private final CartItemJpaRepository itemJpaRepository;

    @Override
    public Optional<Cart> findAllByUserId(Long userId) {
        List<CartItemJpaEntity> entities = itemJpaRepository.findAllByUserId(userId);

        if (entities.isEmpty()) {
            return Optional.empty();
        }

        List<CartItem> items = entities.stream()
                .map(CartItemJpaEntity::toDomain)
                .toList();

        Cart cart = Cart.create(userId, items);
        return Optional.of(cart);
    }

    @Override
    public void save(Cart cart) {
        Long userId = cart.getUserId();

        itemJpaRepository.deleteAllByUserId(userId);

        List<CartItemJpaEntity> entities = cart.getCartItems().stream()
                .map(item -> CartItemJpaEntity.fromDomain(userId, item))
                .toList();

        itemJpaRepository.saveAll(entities);
    }
}

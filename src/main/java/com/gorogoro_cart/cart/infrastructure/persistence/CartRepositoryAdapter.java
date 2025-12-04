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
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {
    private final CartItemJpaRepository itemJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> findByUserId(Long userId) {
        List<CartItemJpaEntity> entities = itemJpaRepository.findByUserId(userId);

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
    @Transactional
    public void save(Cart cart) {
        Long userId = cart.getUserId();

        itemJpaRepository.deleteByUserId(userId);

        List<CartItemJpaEntity> entities = cart.getCartItems().stream()
                .map(item -> CartItemJpaEntity.fromDomain(userId, item))
                .toList();

        itemJpaRepository.saveAll(entities);
    }
}

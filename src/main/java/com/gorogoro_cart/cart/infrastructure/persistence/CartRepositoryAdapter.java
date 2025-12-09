package com.gorogoro_cart.cart.infrastructure.persistence;

import com.gorogoro_cart.cart.domain.model.entity.Cart;
import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import com.gorogoro_cart.cart.infrastructure.persistence.entity.CartItemJpaEntity;
import com.gorogoro_cart.cart.infrastructure.persistence.jpa.CartItemJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

        return Optional.of(Cart.create(userId, items));
    }

    @Override
    public void save(Cart targetCart, List<CartItem> baselineItems) {
        Long userId = targetCart.getUserId();

        Set<Long> baselineCourseIds = toCourseIdSet(baselineItems);
        List<CartItem> targetItems = targetCart.getItems();
        Set<Long> targetCourseIds = toCourseIdSet(targetItems);

        List<CartItemJpaEntity> entitiesToInsert = targetItems.stream()
                .filter(item -> !baselineCourseIds.contains(item.getCourseId()))
                .map(item -> CartItemJpaEntity.fromDomain(userId, item))
                .toList();

        List<Long> courseIdsToDelete = baselineCourseIds.stream()
                .filter(courseId -> !targetCourseIds.contains(courseId))
                .toList();

        if (!courseIdsToDelete.isEmpty()) {
            itemJpaRepository.deleteByUserIdAndCourseIdIn(userId, courseIdsToDelete);
        }

        if (!entitiesToInsert.isEmpty()) {
            itemJpaRepository.saveAll(entitiesToInsert);
        }
    }

    private static Set<Long> toCourseIdSet(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getCourseId)
                .collect(Collectors.toSet());
    }
}

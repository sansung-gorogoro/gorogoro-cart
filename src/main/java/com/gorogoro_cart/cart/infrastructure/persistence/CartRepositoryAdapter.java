package com.gorogoro_cart.cart.infrastructure.persistence;

import com.gorogoro_cart.cart.domain.model.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import com.gorogoro_cart.cart.infrastructure.persistence.jpa.CartJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {
    private final CartJpaRepository cartJpaRepository;

    @Override
    public void save(CartItem item) {
        cartJpaRepository.save(item);
    }

    @Override
    public boolean existsByUserIdAndCourseId(Long userId, Long courseId) {
        return cartJpaRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        cartJpaRepository.deleteAllByUserId(userId);
    }

    @Override
    public void deleteByUserIdAndCourseId(Long userId, Long courseId) {
        cartJpaRepository.deleteByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public List<CartItem> findAllByUserId(Long userId) {
        return cartJpaRepository.findAllByUserId(userId);
    }
}

package com.gorogoro_cart.cart.domain.repository;

import com.gorogoro_cart.cart.domain.model.CartItem;
import org.springframework.data.jpa.repository.Modifying;

public interface CartRepository {
    void save(CartItem item);

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    @Modifying
    void deleteAllByUserId(Long userId);

    void deleteByUserIdAndCourseId(Long userId, Long courseId);
}

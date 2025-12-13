package com.gorogoro_cart.cart.domain.repository;

import com.gorogoro_cart.cart.domain.model.CartItem;
import java.util.List;

public interface CartRepository {
    void save(CartItem item);

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    void deleteAllByUserId(Long userId);

    void deleteByUserIdAndCourseId(Long userId, Long courseId);

    void deleteByCourseId(Long courseId);

    boolean existsByCourseId(Long courseId);

    List<CartItem> findAllByUserId(Long userId);
}

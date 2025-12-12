package com.gorogoro_cart.cart.infrastructure.persistence.jpa;

import com.gorogoro_cart.cart.domain.model.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<CartItem, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    void deleteByUserIdAndCourseId(Long userId, Long courseId);

    void deleteAllByUserId(Long userId);

    void deleteByCourseId(Long courseId);

    boolean existsByCourseId(Long courseId);

    List<CartItem> findAllByUserId(Long userId);
}

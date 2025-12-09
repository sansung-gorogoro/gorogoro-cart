package com.gorogoro_cart.cart.infrastructure.persistence.jpa;

import com.gorogoro_cart.cart.infrastructure.persistence.entity.CartItemJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJpaRepository extends JpaRepository<CartItemJpaEntity, Long> {
    List<CartItemJpaEntity> findAllByUserId(Long userId);

    void deleteByUserIdAndCourseIdIn(Long userId, List<Long> courseIds);
}

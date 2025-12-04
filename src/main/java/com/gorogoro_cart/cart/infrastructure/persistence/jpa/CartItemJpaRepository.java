package com.gorogoro_cart.cart.infrastructure.persistence.jpa;

import com.gorogoro_cart.cart.infrastructure.persistence.entity.CartItemJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJpaRepository extends JpaRepository<CartItemJpaEntity, Long> {
    List<CartItemJpaEntity> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}

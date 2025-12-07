package com.gorogoro_cart.cart.domain.repository;

import com.gorogoro_cart.cart.domain.model.entity.Cart;
import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findAllByUserId(Long userId);

    /**
     * - targetCart: 도메인에서 최종 상태로 완성된 Cart
     * <p>
     * - baselineItems: 저장 직전에 이미 알고 있는 기존 상태(서비스가 조회한 원본)
     */
    void save(Cart targetCart, List<CartItem> baselineItems);
}

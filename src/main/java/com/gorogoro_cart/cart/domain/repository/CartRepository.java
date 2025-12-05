package com.gorogoro_cart.cart.domain.repository;

import com.gorogoro_cart.cart.domain.model.entity.Cart;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findAllByUserId(Long userId);

    void save(Cart cart);
}

package com.gorogoro_cart.cart.domain.model.entity;

import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Cart {
    private final Long id;
    private final Long userId;
    private final List<CartItem> cartItems = new ArrayList<>();

    private Cart(Long id, Long userId, List<CartItem> cartItems) {
        this.id = id;
        this.userId = userId;
        if (cartItems != null) {
            this.cartItems.addAll(cartItems);
        }
    }
}

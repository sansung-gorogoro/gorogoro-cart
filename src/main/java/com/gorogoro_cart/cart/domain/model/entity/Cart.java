package com.gorogoro_cart.cart.domain.model.entity;

import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Cart {
    private final Long userId;
    private final List<CartItem> cartItems = new ArrayList<>();

    private Cart(Long userId, List<CartItem> cartItems) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null.");
        if (cartItems != null) {
            this.cartItems.addAll(cartItems);
        }
    }

    public static Cart create(Long userId) {
        return new Cart(userId, new ArrayList<>());
    }

    public static Cart create(Long userId, List<CartItem> cartItems) {
        return new Cart(userId, cartItems);
    }

    public void addCourse(Long courseId) {
        Objects.requireNonNull(courseId, "courseId must not be null");
        boolean exists = cartItems.stream()
                .anyMatch(item -> item.getCourseId().equals(courseId));

        if (!exists) {
            cartItems.add(CartItem.of(courseId, Instant.now()));
        }
    }

    public void removeCourse(Long courseId) {
        cartItems.removeIf(item -> item.getCourseId().equals(courseId));
    }

    public void clear() {
        cartItems.clear();
    }

    public int size() {
        return cartItems.size();
    }

    public List<CartItem> getItems() {
        return List.copyOf(cartItems);
    }
}

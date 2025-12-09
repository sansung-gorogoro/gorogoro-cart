package com.gorogoro_cart.cart.domain.model.entity;

import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Cart {

    @Getter
    private final Long userId;
    private final List<CartItem> cartItems;

    private Cart(Long userId, List<CartItem> cartItems) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null.");
        this.cartItems = new ArrayList<>(cartItems);
    }

    public static Cart create(Long userId) {
        return new Cart(userId, new ArrayList<>());
    }

    public static Cart create(Long userId, List<CartItem> cartItems) {
        return new Cart(userId, cartItems);
    }

    public void addCourse(Long courseId) {
        Objects.requireNonNull(courseId, "courseId must not be null");
        boolean itemExists = cartItems.stream()
                .anyMatch(item -> item.getCourseId().equals(courseId));

        if (!itemExists) {
            log.info("Cart_log : add duplicated course to user's cart.");
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

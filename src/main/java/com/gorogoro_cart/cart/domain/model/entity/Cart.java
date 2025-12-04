package com.gorogoro_cart.cart.domain.model.entity;

import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Cart {
    private final Long id;
    private final Long userId;
    private final List<CartItem> cartItems = new ArrayList<>();

    private Cart(Long id, Long userId, List<CartItem> cartItems) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null.");
        if (cartItems != null) {
            this.cartItems.addAll(cartItems);
        }
    }

    public static Cart create(Long userId) {
        return new Cart(null, userId, new ArrayList<>());
    }

    public void addCourse(Long courseId) {
        Objects.requireNonNull(courseId, "courseId must not be null");
        boolean exists = cartItems.stream()
                .anyMatch(item -> item.getCourseId().equals(courseId));

        // TODO: 이 부분 멱등성 처리 고민
        if (!exists) {
            cartItems.add(CartItem.of(courseId, LocalDateTime.now()));
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

package com.gorogoro_cart.cart.domain.model.vo;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class CartItem {
    private final Long courseId;
    private final LocalDateTime addedAt;

    private CartItem(Long courseId, LocalDateTime addedAt) {
        this.courseId = Objects.requireNonNull(courseId, "courseId must not be null.");
        this.addedAt = addedAt != null ? addedAt : LocalDateTime.now();
    }

    public static CartItem of(Long courseId, LocalDateTime addedAt) {
        return new CartItem(courseId, addedAt);
    }

}

package com.gorogoro_cart.cart.domain.model.vo;

import java.time.Instant;
import java.util.Objects;
import lombok.Getter;

@Getter
public class CartItem {
    private final Long courseId;
    private final Instant addedAt;

    private CartItem(Long courseId, Instant addedAt) {
        this.courseId = Objects.requireNonNull(courseId, "courseId must not be null.");
        this.addedAt = Objects.requireNonNull(addedAt, "addedAt must not be null.");
    }

    public static CartItem of(Long courseId, Instant addedAt) {
        return new CartItem(courseId, addedAt);
    }

}

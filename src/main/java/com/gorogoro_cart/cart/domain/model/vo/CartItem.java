package com.gorogoro_cart.cart.domain.model.vo;

import java.time.LocalDateTime;

public record CartItem(
        Long courseId,
        LocalDateTime addedAt
) {
}

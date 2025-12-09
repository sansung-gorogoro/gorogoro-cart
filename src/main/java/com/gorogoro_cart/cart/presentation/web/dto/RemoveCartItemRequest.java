package com.gorogoro_cart.cart.presentation.web.dto;

import jakarta.validation.constraints.NotNull;

public record RemoveCartItemRequest(
        @NotNull(message = "강좌 ID는 null일 수 없습니다.")
        Long courseId
) {
}

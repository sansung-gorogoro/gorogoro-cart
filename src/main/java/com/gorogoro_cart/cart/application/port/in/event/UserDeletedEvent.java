package com.gorogoro_cart.cart.application.port.in.event;

import jakarta.validation.constraints.NotNull;

public record UserDeletedEvent(
        @NotNull(message = "회원 ID는 null일 수 없습니다.")
        Long userId
) {
}

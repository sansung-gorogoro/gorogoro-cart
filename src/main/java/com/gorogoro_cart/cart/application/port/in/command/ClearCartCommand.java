package com.gorogoro_cart.cart.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record ClearCartCommand(
        @NotNull(message = "사용자 ID는 null일 수 없습니다.") Long userId
) {
}

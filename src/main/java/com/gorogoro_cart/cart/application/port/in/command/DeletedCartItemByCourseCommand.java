package com.gorogoro_cart.cart.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record DeletedCartItemByCourseCommand(
        @NotNull(message = "강좌 ID는 null일 수 없습니다.")
        Long courseId
) {
}

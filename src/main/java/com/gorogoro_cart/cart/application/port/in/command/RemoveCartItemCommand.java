package com.gorogoro_cart.cart.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record RemoveCartItemCommand(
        @NotNull Long userId,
        @NotNull Long courseId
) {
}

package com.gorogoro_cart.cart.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record RemoveCartItemCommand(
        @NotNull(message = "user id must not be null") Long userId,
        @NotNull(message = "course id must not be null") Long courseId
) {
}

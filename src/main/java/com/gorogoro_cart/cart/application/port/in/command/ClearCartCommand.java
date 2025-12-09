package com.gorogoro_cart.cart.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record ClearCartCommand(
        @NotNull Long userId
) {
}

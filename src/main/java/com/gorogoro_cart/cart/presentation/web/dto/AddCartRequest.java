package com.gorogoro_cart.cart.presentation.web.dto;

import jakarta.validation.constraints.NotNull;

public record AddCartRequest(
        @NotNull(message = "course_id must not be null")
        Long courseId
) {
}

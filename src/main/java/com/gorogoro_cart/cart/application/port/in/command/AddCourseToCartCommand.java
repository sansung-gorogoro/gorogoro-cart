package com.gorogoro_cart.cart.application.port.in.command;

public record AddCourseToCartCommand(
        Long userId,
        Long courseId
) {
}

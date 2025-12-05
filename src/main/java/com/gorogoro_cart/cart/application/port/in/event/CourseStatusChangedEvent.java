package com.gorogoro_cart.cart.application.port.in.event;

import jakarta.validation.constraints.NotNull;

public record CourseStatusChangedEvent(
        @NotNull Long courseId,
        String newStatus
) {
}

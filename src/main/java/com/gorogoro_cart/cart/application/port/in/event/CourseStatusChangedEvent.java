package com.gorogoro_cart.cart.application.port.in.event;

import jakarta.validation.constraints.NotNull;

public record CourseStatusChangedEvent(
        @NotNull(message = "course id must not be null") Long courseId,
        String newStatus
) {
}

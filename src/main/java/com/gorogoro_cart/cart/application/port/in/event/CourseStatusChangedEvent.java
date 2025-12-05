package com.gorogoro_cart.cart.application.port.in.event;

public record CourseStatusChangedEvent(
        Long courseId,
        String newStatus
) {
}

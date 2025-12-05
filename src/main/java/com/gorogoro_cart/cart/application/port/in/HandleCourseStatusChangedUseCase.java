package com.gorogoro_cart.cart.application.port.in;

import com.gorogoro_cart.cart.application.port.in.event.CourseStatusChangedEvent;

public interface HandleCourseStatusChangedUseCase {
    void handleCourseStatusChanged(CourseStatusChangedEvent event);
}

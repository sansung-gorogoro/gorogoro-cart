package com.gorogoro_cart.cart.application.port.in;

import com.gorogoro_cart.cart.application.port.in.event.CourseDeletedEvent;

public interface HandleCourseDeletedUseCase {
    void handleCourseStatusChanged(CourseDeletedEvent event);
}

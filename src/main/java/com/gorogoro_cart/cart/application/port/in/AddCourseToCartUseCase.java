package com.gorogoro_cart.cart.application.port.in;

import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;

public interface AddCourseToCartUseCase {
    void addCourse(AddCourseToCartCommand addCourseToCartCommand);
}

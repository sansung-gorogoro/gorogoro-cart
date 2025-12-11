package com.gorogoro_cart.cart.application.port.out;

import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import java.util.List;

public interface CoursePort {
    List<CourseDetailDto> findCourseDetailsByIds(List<Long> courseIds);
}

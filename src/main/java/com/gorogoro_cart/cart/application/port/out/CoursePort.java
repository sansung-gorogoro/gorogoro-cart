package com.gorogoro_cart.cart.application.port.out;

import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import java.util.List;

public interface CoursePort {

    /**
     * 주어진 courseId를 가진 강의가 현재 구매 가능한 상태인지 확인합니다.
     *
     * @param courseId 확인할 강의의 ID
     * @return 강의가 구매 가능하면 true, 그렇지 않으면 false
     */
    boolean isCoursePurchasable(Long courseId);

    List<CourseDetailDto> findCourseDetailsByIds(List<Long> courseIds);
}

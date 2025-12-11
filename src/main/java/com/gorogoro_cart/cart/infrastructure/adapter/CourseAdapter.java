package com.gorogoro_cart.cart.infrastructure.adapter;

import com.gorogoro_cart.cart.application.port.out.CoursePort;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import com.gorogoro_cart.cart.common.exception.BusinessException;
import com.gorogoro_cart.cart.common.exception.ErrorCode;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseAdapter implements CoursePort {
    private final CourseFeignClient courseFeignClient;

    @Override
    @CircuitBreaker(name = "courseClient", fallbackMethod = "fallbackCourseDetails")
    public List<CourseDetailDto> findCourseDetailsByIds(List<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            throw new BusinessException(ErrorCode.MISSING_REQUEST_PARAMETER, "courseIds");
        }
        try {
            return courseFeignClient.findCourseDetailsByIds(courseIds);
        } catch (FeignException e) {
            String body = e.contentUTF8();
            String detail = "Course service error. status=%s, body=%s".formatted(e.status(), body);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, detail);
        }
    }

    @SuppressWarnings("unused")
    private List<CourseDetailDto> fallbackCourseDetails(List<Long> courseIds, Throwable throwable) {
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "Course service fallback triggered: " + throwable.getMessage());
    }
}

package com.gorogoro_cart.cart.infrastructure.adapter;

import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "course-client",
        url = "${spring.cloud.openfeign.client.config.course-service.url}"
)
public interface CourseFeignClient {

    @GetMapping
    List<CourseDetailDto> findCourseDetailsByIds(@RequestParam("courseIds") List<Long> courseIds);
}

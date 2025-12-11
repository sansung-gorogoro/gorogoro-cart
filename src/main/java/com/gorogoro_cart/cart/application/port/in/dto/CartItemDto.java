package com.gorogoro_cart.cart.application.port.in.dto;

import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import com.gorogoro_cart.cart.domain.model.CartItem;
import java.time.Instant;
import java.util.Objects;

public record CartItemDto(
        CourseDetailDto.CategoryDetail categoryDetail,
        Long courseId,
        String courseTitle,
        String instructorName,
        Integer price,
        String coverImgUrl,
        Instant addedAt
) {
    public static CartItemDto of(CartItem cartItem, CourseDetailDto courseDetail) {
        CourseDetailDto.CategoryDetail categoryDetail = Objects.requireNonNull(
                courseDetail.categoryDetail(),
                () -> "Category detail is required for courseId %d".formatted(cartItem.getCourseId())
        );
        return new CartItemDto(
                categoryDetail,
                cartItem.getCourseId(),
                courseDetail.courseTitle(),
                courseDetail.instructorName(),
                courseDetail.price(),
                courseDetail.coverImgUrl(),
                cartItem.getAddedAt()
        );
    }
}

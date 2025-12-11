package com.gorogoro_cart.cart.application.port.in.dto;

import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import com.gorogoro_cart.cart.domain.model.CartItem;
import java.time.Instant;

public record CartItemDto(
        String categoryName,
        String subCategoryName,
        Long courseId,
        String courseTitle,
        String instructorName,
        Integer price,
        String coverImgUrl,
        Instant addedAt
) {
    public static CartItemDto of(CartItem cartItem, CourseDetailDto courseDetail) {
        return new CartItemDto(
                courseDetail.categoryName(),
                courseDetail.subCategoryName(),
                cartItem.getCourseId(),
                courseDetail.courseTitle(),
                courseDetail.instructorName(),
                courseDetail.price(),
                courseDetail.coverImgUrl(),
                cartItem.getAddedAt()
        );
    }
}

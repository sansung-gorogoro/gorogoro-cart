package com.gorogoro_cart.cart.application.port.in.dto;

import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto.CategoryDetail;
import com.gorogoro_cart.cart.domain.model.CartItem;
import java.time.Instant;
import java.util.Objects;

public record CartItemDto(
        CategoryDetail categoryDetail,
        Long courseId,
        String courseTitle,
        String instructorName,
        Integer price,
        String coverImgUrl,
        Instant addedAt
) {
    public static CartItemDto of(CartItem cartItem, CourseDetailDto courseDetail) {
        CategoryDetail categoryDetail = requireCategoryDetail(cartItem, courseDetail);
        Integer price = requirePrice(cartItem, courseDetail);
        return new CartItemDto(
                categoryDetail,
                cartItem.getCourseId(),
                courseDetail.courseTitle(),
                courseDetail.instructorName(),
                price,
                courseDetail.coverImgUrl(),
                cartItem.getAddedAt()
        );
    }

    private static CategoryDetail requireCategoryDetail(CartItem cartItem, CourseDetailDto courseDetail) {
        return Objects.requireNonNull(
                courseDetail.categoryDetail(),
                () -> "카테고리 정보가 없습니다. courseId: %d".formatted(cartItem.getCourseId())
        );
    }

    private static Integer requirePrice(CartItem cartItem, CourseDetailDto courseDetail) {
        return Objects.requireNonNull(
                courseDetail.price(),
                () -> "가격 정보가 없습니다. courseId: %d".formatted(cartItem.getCourseId())
        );
    }
}

package com.gorogoro_cart.cart.presentation.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gorogoro_cart.cart.application.port.in.dto.CartDetailsDto;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import java.util.List;
import java.util.Objects;

public record CartResponse(
        @JsonProperty("owner_id")
        Long ownerId,
        List<CartItemResponse> items,
        SummaryResponse summary
) {
    public static CartResponse from(CartDetailsDto cartDetails) {
        var items = cartDetails.items().stream()
                .map(itemDto -> new CartItemResponse(
                        mapToCategoryDetailResponse(itemDto.categoryDetail()),
                        itemDto.courseId(),
                        itemDto.courseTitle(),
                        itemDto.instructorName(),
                        itemDto.price(),
                        itemDto.coverImgUrl(),
                        itemDto.addedAt()
                ))
                .toList();

        var summary = new SummaryResponse(
                cartDetails.summary().totalCount(),
                cartDetails.summary().totalAmount()
        );

        return new CartResponse(cartDetails.ownerId(), items, summary);
    }

    private static CartItemResponse.CategoryDetailResponse mapToCategoryDetailResponse(
            CourseDetailDto.CategoryDetail rootCategory) {
        Objects.requireNonNull(rootCategory, "categoryDetail must not be null");
        var subCategory = Objects.requireNonNull(
                rootCategory.subCategoryDetail(),
                "subcategoryDetail must not be null"
        );
        return new CartItemResponse.CategoryDetailResponse(
                rootCategory.categoryId(),
                rootCategory.name(),
                new CartItemResponse.SubCategoryDetailResponse(
                        subCategory.subCategoryId(),
                        subCategory.name()));
    }
}

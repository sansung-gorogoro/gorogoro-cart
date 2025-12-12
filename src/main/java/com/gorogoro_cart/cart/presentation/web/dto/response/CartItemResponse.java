package com.gorogoro_cart.cart.presentation.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gorogoro_cart.cart.application.port.in.dto.CartItemDto;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import java.time.Instant;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartItemResponse(
        CategoryDetailResponse categoryDetail,
        Long courseId,
        String courseTitle,
        String instructorName,
        Integer price,
        String coverImgUrl,
        Instant addedAt
) {

    public static CartItemResponse from(CartItemDto dto) {
        return new CartItemResponse(
                mapToCategoryDetailResponse(dto.categoryDetail()),
                dto.courseId(),
                dto.courseTitle(),
                dto.instructorName(),
                dto.price(),
                dto.coverImgUrl(),
                dto.addedAt()
        );
    }

    private static CategoryDetailResponse mapToCategoryDetailResponse(
            CourseDetailDto.CategoryDetail rootCategory) {
        Objects.requireNonNull(rootCategory, "상위 카테고리 정보가 없습니다.");
        var subCategory = Objects.requireNonNull(
                rootCategory.subCategoryDetail(),
                "하위 카테고리 정보가 없습니다."
        );
        return new CategoryDetailResponse(
                rootCategory.categoryId(),
                rootCategory.name(),
                new SubCategoryDetailResponse(
                        subCategory.subCategoryId(),
                        subCategory.name()));
    }

    public record CategoryDetailResponse(
            @JsonProperty("categoryId") Long categoryId,
            @JsonProperty("name") String name,
            @JsonProperty("subCategoryDetail") SubCategoryDetailResponse subCategoryDetail
    ) {
    }

    public record SubCategoryDetailResponse(
            @JsonProperty("subCategoryId") Long subCategoryId,
            @JsonProperty("name") String name
    ) {
    }
}

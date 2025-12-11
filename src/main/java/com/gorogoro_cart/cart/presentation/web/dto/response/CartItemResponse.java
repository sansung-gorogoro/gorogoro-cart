package com.gorogoro_cart.cart.presentation.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

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

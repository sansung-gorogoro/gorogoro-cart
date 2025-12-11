package com.gorogoro_cart.cart.application.port.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CourseDetailDto(
        @JsonProperty("courseId") Long courseId,
        @JsonProperty("title") String courseTitle,
        @JsonProperty("instructorName") String instructorName,
        @JsonProperty("price") Integer price,
        @JsonProperty("coverImageUrl") String coverImgUrl,
        @JsonProperty("categoryDetail") CategoryDetail categoryDetail
) {
    public record CategoryDetail(
            @JsonProperty("categoryId") Long categoryId,
            @JsonProperty("name") String name,
            @JsonProperty("subCategoryDetailDto") SubCategoryDetail subCategoryDetail
    ) {
    }

    public record SubCategoryDetail(
            @JsonProperty("subCategoryId") Long subCategoryId,
            @JsonProperty("name") String name
    ) {
    }
}

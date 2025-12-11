package com.gorogoro_cart.cart.application.port.out.dto;

public record CourseDetailDto(
        String categoryName,
        String subCategoryName,
        Long courseId,
        String courseTitle,
        String instructorName,
        Integer price,
        String coverImgUrl
) {
}

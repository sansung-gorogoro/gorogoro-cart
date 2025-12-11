package com.gorogoro_cart.cart.presentation.web.dto.response;

import java.time.Instant;

public record CartItemResponse(
        String categoryName,
        String subCategoryName,
        Long courseId,
        String courseTitle,
        String instructorName,
        Integer price,
        String coverImgUrl,
        Instant addedAt
) {
}

package com.gorogoro_cart.cart.presentation.web.dto.response;

public record SummaryResponse(
        int totalCount,
        Integer totalAmount
) {
}

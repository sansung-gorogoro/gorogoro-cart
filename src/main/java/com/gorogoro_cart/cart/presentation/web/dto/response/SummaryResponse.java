package com.gorogoro_cart.cart.presentation.web.dto.response;

import com.gorogoro_cart.cart.application.port.in.dto.SummaryDto;

public record SummaryResponse(
        int totalCount,
        Integer totalAmount
) {
    public static SummaryResponse from(SummaryDto dto) {
        return new SummaryResponse(dto.totalCount(), dto.totalAmount());
    }
}

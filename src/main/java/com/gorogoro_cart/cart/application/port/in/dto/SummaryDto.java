package com.gorogoro_cart.cart.application.port.in.dto;

import java.util.List;

public record SummaryDto(
    int totalCount,
    Integer totalAmount
) {
    public static SummaryDto from(List<CartItemDto> items) {
        int totalAmount = items.stream().mapToInt(CartItemDto::price).sum();
        return new SummaryDto(items.size(), totalAmount);
    }
}

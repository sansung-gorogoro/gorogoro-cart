package com.gorogoro_cart.cart.application.port.in.dto;

import java.util.List;

public record CartDetailsDto(
    Long cartId,
    List<CartItemDto> items,
    SummaryDto summary
) {
}

package com.gorogoro_cart.cart.application.port.in.dto;

import java.util.List;

public record CartDetailsDto(
    Long ownerId,
    List<CartItemDto> items,
    SummaryDto summary
) {
}

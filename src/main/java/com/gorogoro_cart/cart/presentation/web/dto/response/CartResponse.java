package com.gorogoro_cart.cart.presentation.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gorogoro_cart.cart.application.port.in.dto.CartDetailsDto;
import java.util.List;

public record CartResponse(
        @JsonProperty("owner_id")
        Long ownerId,
        List<CartItemResponse> items,
        SummaryResponse summary
) {
    public static CartResponse from(CartDetailsDto cartDetails) {
        List<CartItemResponse> items = cartDetails.items().stream()
                .map(CartItemResponse::from)
                .toList();

        SummaryResponse summary = SummaryResponse.from(cartDetails.summary());

        return new CartResponse(cartDetails.ownerId(), items, summary);
    }
}

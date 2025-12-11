package com.gorogoro_cart.cart.presentation.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gorogoro_cart.cart.application.port.in.dto.CartDetailsDto;
import java.util.List;
import java.util.stream.Collectors;

public record CartResponse(
        @JsonProperty("cart_id")
        Long cartId,
        List<CartItemResponse> items,
        SummaryResponse summary
) {
    public static CartResponse from(CartDetailsDto cartDetails) {
        var items = cartDetails.items().stream()
                .map(itemDto -> new CartItemResponse(
                        itemDto.categoryName(),
                        itemDto.subCategoryName(),
                        itemDto.courseId(),
                        itemDto.courseTitle(),
                        itemDto.instructorName(),
                        itemDto.price(),
                        itemDto.coverImgUrl(),
                        itemDto.addedAt()
                ))
                .collect(Collectors.toList());

        var summary = new SummaryResponse(
                cartDetails.summary().totalCount(),
                cartDetails.summary().totalAmount()
        );

        return new CartResponse(cartDetails.cartId(), items, summary);
    }
}

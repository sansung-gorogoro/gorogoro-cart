package com.gorogoro_cart.cart.application.port.in;

import com.gorogoro_cart.cart.application.port.in.dto.CartDetailsDto;

public interface FindCartItemsUseCase {
    CartDetailsDto findCartItems(Long userId);
}

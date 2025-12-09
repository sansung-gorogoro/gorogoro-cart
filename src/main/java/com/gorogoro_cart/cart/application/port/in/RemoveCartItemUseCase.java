package com.gorogoro_cart.cart.application.port.in;

import com.gorogoro_cart.cart.application.port.in.command.RemoveCartItemCommand;

public interface RemoveCartItemUseCase {
    void removeCartItem(RemoveCartItemCommand removeCartItemCommand);
}

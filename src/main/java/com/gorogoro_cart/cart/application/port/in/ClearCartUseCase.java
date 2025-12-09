package com.gorogoro_cart.cart.application.port.in;

import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;

public interface ClearCartUseCase {
    void clearCart(ClearCartCommand clearCartCommand);
}

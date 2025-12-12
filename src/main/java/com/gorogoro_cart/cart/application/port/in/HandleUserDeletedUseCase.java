package com.gorogoro_cart.cart.application.port.in;

import com.gorogoro_cart.cart.application.port.in.event.UserDeletedEvent;

public interface HandleUserDeletedUseCase {
    void handleUserDeleted(UserDeletedEvent event);
}

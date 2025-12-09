package com.gorogoro_cart.cart.application.service;

import com.gorogoro_cart.cart.application.port.in.AddCourseToCartUseCase;
import com.gorogoro_cart.cart.application.port.in.ClearCartUseCase;
import com.gorogoro_cart.cart.application.port.in.RemoveCartItemUseCase;
import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.RemoveCartItemCommand;
import com.gorogoro_cart.cart.domain.model.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartCommandService implements AddCourseToCartUseCase, ClearCartUseCase, RemoveCartItemUseCase {
    private final CartRepository cartRepository;

    @Override
    public void addCourse(AddCourseToCartCommand command) {
        if (cartRepository.existsByUserIdAndCourseId(command.userId(), command.courseId())) {
            log.debug("Duplicated cart item. userId: {}, courseId: {}", command.userId(), command.courseId());
            return;
        }
        CartItem newItem = CartItem.of(command.userId(), command.courseId(), Instant.now());
        cartRepository.save(newItem);
    }

    @Override
    public void clearCart(ClearCartCommand command) {
        cartRepository.deleteAllByUserId(command.userId());
    }

    @Override
    public void removeCartItem(RemoveCartItemCommand command) {
        cartRepository.deleteByUserIdAndCourseId(command.userId(), command.courseId());
    }
}

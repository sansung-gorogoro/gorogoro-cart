package com.gorogoro_cart.cart.application.service;

import com.gorogoro_cart.cart.application.port.in.AddCourseToCartUseCase;
import com.gorogoro_cart.cart.application.port.in.ClearCartUseCase;
import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.domain.model.entity.Cart;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartCommandService implements AddCourseToCartUseCase, ClearCartUseCase {
    private final CartRepository cartRepository;

    @Override
    public void addCourse(AddCourseToCartCommand command) {
        Cart cart = getCart(command.userId());
        cart.addCourse(command.courseId());
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(ClearCartCommand command) {
        Cart cart = getCart(command.userId());
        cart.clear();
        cartRepository.save(cart);
    }

    private Cart getCart(Long userId) {
        return cartRepository.findAllByUserId(userId)
                .orElseGet(() -> Cart.create(userId));
    }
}

package com.gorogoro_cart.cart.application.service;

import com.gorogoro_cart.cart.application.port.in.AddCourseToCartUseCase;
import com.gorogoro_cart.cart.application.port.in.ClearCartUseCase;
import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.domain.model.entity.Cart;
import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import java.util.List;
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
        Long userId = command.userId();

        List<CartItem> baselineItems = loadBaselineItems(userId);
        Cart targetCart = buildTargetCart(userId, baselineItems);
        targetCart.addCourse(command.courseId());

        cartRepository.save(targetCart, baselineItems);
    }

    @Override
    public void clearCart(ClearCartCommand command) {
        Long userId = command.userId();

        List<CartItem> baselineItems = loadBaselineItems(userId);
        Cart targetCart = buildTargetCart(userId, baselineItems);
        targetCart.clear();

        cartRepository.save(targetCart, baselineItems);
    }

    private List<CartItem> loadBaselineItems(Long userId) {
        return cartRepository.findAllByUserId(userId)
                .map(Cart::getItems)
                .orElseGet(List::of);
    }

    private Cart buildTargetCart(Long userId, List<CartItem> baselineItems) {
        if (baselineItems.isEmpty()) {
            return Cart.create(userId);
        }
        return Cart.create(userId, baselineItems);
    }
}

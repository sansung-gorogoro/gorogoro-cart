package com.gorogoro_cart.cart.presentation.web;

import com.gorogoro_cart.cart.application.port.in.AddCourseToCartUseCase;
import com.gorogoro_cart.cart.application.port.in.ClearCartUseCase;
import com.gorogoro_cart.cart.application.port.in.RemoveCartItemUseCase;
import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.RemoveCartItemCommand;
import com.gorogoro_cart.cart.presentation.web.dto.AddCartRequest;
import com.gorogoro_cart.cart.presentation.web.dto.RemoveCartItemRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/carts")
@RequiredArgsConstructor
public class CartController {
    private static final String HEADER_USER_ID = "X-User-Id";

    private final AddCourseToCartUseCase addCourseToCartUseCase;
    private final ClearCartUseCase clearCartUseCase;
    private final RemoveCartItemUseCase removeCartItemUseCase;

    @PostMapping
    public ResponseEntity<Void> addCourseToCart(
            @RequestHeader(HEADER_USER_ID) Long userId,
            @Valid @RequestBody AddCartRequest request
    ) {
        AddCourseToCartCommand command = new AddCourseToCartCommand(userId, request.courseId());
        addCourseToCartUseCase.addCourse(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @RequestHeader(HEADER_USER_ID) Long userId
    ) {
        ClearCartCommand command = new ClearCartCommand(userId);
        clearCartUseCase.clearCart(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items")
    public ResponseEntity<Void> removeCartItem(
            @RequestHeader(HEADER_USER_ID) Long userId,
            @Valid @RequestBody RemoveCartItemRequest request
    ) {
        RemoveCartItemCommand command = new RemoveCartItemCommand(userId, request.courseId());
        removeCartItemUseCase.removeCartItem(command);
        return ResponseEntity.noContent().build();
    }
}

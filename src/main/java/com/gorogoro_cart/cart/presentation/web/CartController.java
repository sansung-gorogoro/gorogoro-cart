package com.gorogoro_cart.cart.presentation.web;

import com.gorogoro_cart.cart.application.port.in.AddCourseToCartUseCase;
import com.gorogoro_cart.cart.application.port.in.ClearCartUseCase;
import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.presentation.web.dto.AddCartRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final AddCourseToCartUseCase addCourseToCartUseCase;
    private final ClearCartUseCase clearCartUseCase;

    // TODO : userId 유무 확인 필요 -> 임시로 PathVariable 사용
    @PostMapping("/{userId}")
    public ResponseEntity<Void> addCourseToCart(
            @PathVariable Long userId,
            @Valid @RequestBody AddCartRequest request
    ) {
        AddCourseToCartCommand command = new AddCourseToCartCommand(userId, request.courseId());
        addCourseToCartUseCase.addCourse(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(
            @PathVariable Long userId
    ) {
        ClearCartCommand command = new ClearCartCommand(userId);
        clearCartUseCase.clearCart(command);
        return ResponseEntity.noContent().build();
    }
}

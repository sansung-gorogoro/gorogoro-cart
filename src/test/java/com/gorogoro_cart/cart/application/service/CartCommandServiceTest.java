package com.gorogoro_cart.cart.application.service;

import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.domain.model.entity.Cart;
import com.gorogoro_cart.cart.domain.model.vo.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartCommandServiceTest {

    @InjectMocks
    private CartCommandService cartCommandService;

    @Mock
    private CartRepository cartRepository;

    @Test
    @DisplayName("장바구니에 강좌를 추가할 수 있다.")
    void shouldAddCourseToCart() {
        // given
        Long userId = 1L;
        Long courseId = 10L;
        AddCourseToCartCommand command = new AddCourseToCartCommand(userId, courseId);

        given(cartRepository.findAllByUserId(userId)).willReturn(empty());

        // when
        cartCommandService.addCourse(command);

        // then
        verify(cartRepository).save(
                argThat(cart -> containsCourse(cart, courseId)),
                eq(List.of())
        );
    }

    private boolean containsCourse(Cart cart, Long courseId) {
        return cart.getItems().stream()
                .map(CartItem::getCourseId)
                .anyMatch(id -> id.equals(courseId));
    }

    // TODO: 이미 수강 중인 강의이거나 판매 중이지 않은 강좌를 장바구니에 등록하려 한 경우 테스트 추가해야함.

    @Test
    @DisplayName("장바구니 전체 비우기가 가능하다.")
    void shouldClearCart() {
        // given
        Long userId = 1L;
        ClearCartCommand command = new ClearCartCommand(userId);

        Cart existingCart = Cart.create(userId);
        existingCart.addCourse(10L);

        given(cartRepository.findAllByUserId(userId)).willReturn(Optional.of(existingCart));

        List<CartItem> baseline = existingCart.getItems();

        // when
        cartCommandService.clearCart(command);

        // then
        verify(cartRepository).save(
                argThat(cart -> cart.getItems().isEmpty()),
                eq(baseline)
        );
    }
}

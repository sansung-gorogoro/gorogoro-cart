package com.gorogoro_cart.cart.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.common.exception.BusinessException;
import com.gorogoro_cart.cart.common.exception.ErrorCode;
import com.gorogoro_cart.cart.domain.model.entity.Cart;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
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

        given(cartRepository.findAllByUserId(userId)).willReturn(Optional.empty());

        // when
        cartCommandService.addCourse(command);

        // then
        verify(cartRepository).save(any(Cart.class));
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

        // when
        cartCommandService.clearCart(command);

        // then
        assertThat(existingCart.getCartItems()).isEmpty();
        verify(cartRepository).save(existingCart);
    }

    @Test
    @DisplayName("장바구니가 존재하지 않을 경우 예외가 발생한다.")
    void shouldThrowException_whenCartNotFound() {
        // given
        Long userId = 1L;
        ClearCartCommand command = new ClearCartCommand(userId);

        given(cartRepository.findAllByUserId(userId)).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> cartCommandService.clearCart(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.CART_NOT_FOUND.getMessage());
    }
}

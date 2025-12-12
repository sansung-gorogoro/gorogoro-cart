package com.gorogoro_cart.cart.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.RemoveCartItemCommand;
import com.gorogoro_cart.cart.common.exception.BusinessException;
import com.gorogoro_cart.cart.common.exception.ErrorCode;
import com.gorogoro_cart.cart.domain.model.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
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

        given(cartRepository.existsByUserIdAndCourseId(userId, courseId)).willReturn(false);

        // when
        cartCommandService.addCourse(command);

        // then
        verify(cartRepository).save(any(CartItem.class));
    }

    // TODO: 이미 수강 중인 강의이거나 판매 중이지 않은 강좌를 장바구니에 등록하려 한 경우 테스트 추가해야함.

    @Test
    @DisplayName("장바구니 전체 비우기가 가능하다.")
    void shouldClearCart() {
        // given
        Long userId = 1L;
        ClearCartCommand command = new ClearCartCommand(userId);

        // when
        cartCommandService.clearCart(command);

        // then
        verify(cartRepository).deleteAllByUserId(userId);
    }

    @Test
    @DisplayName("장바구니에서 특정 아이템 한 개를 삭제할 수 있다.")
    void shouldRemoveSingleItemFromCart() {
        // given
        Long userId = 1L;
        Long courseId = 10L;
        RemoveCartItemCommand command = new RemoveCartItemCommand(userId, courseId);
        given(cartRepository.existsByUserIdAndCourseId(userId, courseId)).willReturn(true);

        // when
        cartCommandService.removeCartItem(command);

        // then
        verify(cartRepository).deleteByUserIdAndCourseId(userId, courseId);
    }

    @Test
    @DisplayName("장바구니에 없는 아이템 삭제 시 예외가 발생한다.")
    void shouldThrowWhenRemovingNonExistingItem() {
        // given
        Long userId = 3L;
        Long courseId = 30L;
        RemoveCartItemCommand command = new RemoveCartItemCommand(userId, courseId);
        given(cartRepository.existsByUserIdAndCourseId(userId, courseId)).willReturn(false);

        // when // then
        assertThatThrownBy(() -> cartCommandService.removeCartItem(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.CART_ITEM_NOT_FOUND.getMessage());
    }
}

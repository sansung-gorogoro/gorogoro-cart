package com.gorogoro_cart.cart.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.gorogoro_cart.cart.application.port.in.command.AddCourseToCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.ClearCartCommand;
import com.gorogoro_cart.cart.application.port.in.command.RemoveCartItemCommand;
import com.gorogoro_cart.cart.application.port.in.event.CourseDeletedEvent;
import com.gorogoro_cart.cart.application.port.in.event.UserDeletedEvent;
import com.gorogoro_cart.cart.application.port.out.CoursePort;
import com.gorogoro_cart.cart.application.port.out.dto.CourseDetailDto;
import com.gorogoro_cart.cart.common.exception.BusinessException;
import com.gorogoro_cart.cart.common.exception.ErrorCode;
import com.gorogoro_cart.cart.domain.model.CartItem;
import com.gorogoro_cart.cart.domain.repository.CartRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartCommandServiceTest {

    @InjectMocks
    private CartCommandService cartCommandService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CoursePort coursePort;

    @Test
    @DisplayName("장바구니에 강좌를 추가할 수 있다.")
    void shouldAddCourseToCart() {
        // given
        Long userId = 1L;
        Long courseId = 10L;
        AddCourseToCartCommand command = new AddCourseToCartCommand(userId, courseId);

        given(cartRepository.existsByUserIdAndCourseId(userId, courseId)).willReturn(false);
        given(coursePort.findCourseDetailsByIds(List.of(courseId))).willReturn(List.of(mockCourseDetail(courseId)));

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

    @Test
    @DisplayName("강좌 삭제 이벤트 수신 시 해당 강좌를 장바구니에서 제거한다.")
    void shouldRemoveItemsByCourseIdWhenCourseDeletedEventArrives() {
        // given
        CourseDeletedEvent event = new CourseDeletedEvent(20L);
        given(cartRepository.existsByCourseId(event.courseId())).willReturn(true);

        // when
        cartCommandService.handleCourseStatusChanged(event);

        // then
        verify(cartRepository).deleteByCourseId(event.courseId());
    }

    @Test
    @DisplayName("강좌 삭제 이벤트 수신 시 장바구니에 없으면 삭제를 건너뛴다.")
    void shouldSkipDeletingWhenCourseNotPresent() {
        // given
        CourseDeletedEvent event = new CourseDeletedEvent(21L);
        given(cartRepository.existsByCourseId(event.courseId())).willReturn(false);

        // when
        cartCommandService.handleCourseStatusChanged(event);

        // then
        verify(cartRepository).existsByCourseId(event.courseId());
        org.mockito.Mockito.verifyNoMoreInteractions(cartRepository);
    }

    @Test
    @DisplayName("회원 삭제 이벤트 수신 시 해당 회원의 장바구니를 비운다.")
    void shouldClearCartWhenUserDeletedEventArrives() {
        // given
        UserDeletedEvent event = new UserDeletedEvent(2L);

        // when
        cartCommandService.handleUserDeleted(event);

        // then
        verify(cartRepository).deleteAllByUserId(event.userId());
    }

    @Test
    @DisplayName("존재하지 않는 강좌를 장바구니에 추가하면 예외가 발생한다.")
    void shouldThrowWhenCourseNotFoundOnAdd() {
        // given
        Long userId = 4L;
        Long courseId = 40L;
        AddCourseToCartCommand command = new AddCourseToCartCommand(userId, courseId);
        given(cartRepository.existsByUserIdAndCourseId(userId, courseId)).willReturn(false);
        given(coursePort.findCourseDetailsByIds(ArgumentMatchers.eq(List.of(courseId)))).willReturn(List.of());

        // when // then
        assertThatThrownBy(() -> cartCommandService.addCourse(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.COURSE_NOT_FOUND.getMessage());
    }

    private CourseDetailDto mockCourseDetail(Long courseId) {
        return new CourseDetailDto(
                courseId,
                "title",
                "instructor",
                0,
                "cover",
                null
        );
    }
}

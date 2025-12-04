package com.gorogoro_cart.cart.domain.model.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    @DisplayName("동일한 강좌를 담을 시 무시된다.")
    void shouldNotThrowException_whenAddDuplicateCourse() {
        // given
        Long userId = 1L;
        Long courseId = 1L;
        Cart cart = Cart.create(userId);

        // when
        cart.addCourse(courseId);
        cart.addCourse(courseId);

        // then
        assertThat(cart.size()).isEqualTo(1);
    }

}

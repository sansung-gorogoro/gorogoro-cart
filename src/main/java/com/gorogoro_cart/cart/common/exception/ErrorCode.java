package com.gorogoro_cart.cart.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 404 NOT_FOUND
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 ID에 해당하는 장바구니를 찾을 수 없습니다."),

    // 400 BAD REQUEST
    COURSE_NOT_PURCHASABLE(HttpStatus.BAD_REQUEST, "현재 구매할 수 없는 강의입니다."),
    ALREADY_ENROLLED_COURSE(HttpStatus.BAD_REQUEST, "이미 수강 중인 강의입니다.");

    private final HttpStatus status;
    private final String message;
}

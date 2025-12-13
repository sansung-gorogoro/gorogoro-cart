package com.gorogoro_cart.cart.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST: 잘못된 요청
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다.", "CRT-1000"),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다.", "CRT-1001"),
    COURSE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 강의입니다.", "CRT-1002"),

    // 404 NOT FOUND: 존재하지 않음
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니에 해당 강좌가 없습니다.", "CRT-4000"),

    // 409 CONFLICT: 요청이 서버의 현재 상태와 충돌
    COURSE_NOT_PURCHASABLE(HttpStatus.CONFLICT, "현재 구매할 수 없는 강의입니다.", "CRT-2000"),
    ALREADY_ENROLLED_COURSE(HttpStatus.CONFLICT, "이미 수강 중인 강의입니다.", "CRT-2001"),
    CART_ITEM_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 장바구니에 담긴 강의입니다.", "CRT-2002"),

    // 500 INTERNAL_SERVER_ERROR: 서버 내부 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 예상치 못한 오류가 발생했습니다.", "CRT-5000"),

    // 503 SERVICE_UNAVAILABLE: 외부 서비스 불가
    COURSE_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "강의 서비스가 응답하지 않습니다.", "CRT-5001");

    private final HttpStatus status;
    private final String message;
    private final String code;
}

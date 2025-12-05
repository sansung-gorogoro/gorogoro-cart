package com.gorogoro_cart.cart.presentation.web.handler;

import com.gorogoro_cart.cart.common.exception.BusinessException;
import com.gorogoro_cart.cart.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 직접 정의한 비즈니스 예외를 처리합니다.
     *
     * @param e CustomException 객체
     * @return ErrorCode에 정의된 HttpStatus와 메시지를 담은 ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

    /**
     * 데이터베이스 무결성 제약 조건(예: Unique Key 중복) 위반 시 발생하는 예외를 처리합니다.
     *
     * @param e DataIntegrityViolationException 객체
     * @return 200 OK 상태 코드와 비어있는 응답 본문
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Void> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.info("Duplicate item insert attempt caught and handled gracefully.");
        return ResponseEntity.ok().build();
    }

    private record ErrorResponse(String code, String message) {
    }
}

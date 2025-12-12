package com.gorogoro_cart.cart.presentation.web.handler;

import com.gorogoro_cart.cart.common.exception.BusinessException;
import com.gorogoro_cart.cart.common.exception.ErrorCode;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * <code>@Valid</code> 어노테이션을 사용한 DTO의 유효성 검증 실패 시 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        final ErrorResponse response = ErrorResponse.of(errorCode, e.getBindingResult());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    /**
     * 필수 @RequestParam 파라미터가 누락되었을 때 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
            MissingServletRequestParameterException e) {
        final ErrorCode errorCode = ErrorCode.MISSING_REQUEST_PARAMETER;
        final String message = String.format("필수 파라미터 '%s'(이)가 누락되었습니다.", e.getParameterName());
        final ErrorResponse response = new ErrorResponse(errorCode.name(), message);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    /**
     * 직접 정의한 비즈니스 예외를 처리합니다.
     *
     * @param e BusinessException 객체
     * @return ErrorCode에 정의된 HttpStatus와 메시지를 담은 ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode.name(), errorCode.getMessage()), errorCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        log.error("Unhandled exception occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ErrorResponse(errorCode.name(), errorCode.getMessage()), errorCode.getStatus());
    }

    private record ErrorResponse(String code, String message) {
        public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
            return new ErrorResponse(code.name(), createErrorMessage(bindingResult));
        }

        private static String createErrorMessage(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> String.format("%s", error.getDefaultMessage()))
                    .collect(Collectors.joining(", "));
        }
    }
}

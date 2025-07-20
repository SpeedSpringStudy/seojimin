package com.spring.study.common.exception;

import com.spring.study.common.exception.custonException.KakaoNameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.INPUT_INVALID_VALUE.getStatus())
                .code(ErrorCode.INPUT_INVALID_VALUE.getCode())
                .message(ErrorCode.INPUT_INVALID_VALUE.getMessage())
                .build();
        return ResponseEntity.status(ErrorCode.INPUT_INVALID_VALUE.getStatus()).body(response);
    }

    @ExceptionHandler(KakaoNameException.class)
    public ResponseEntity<ErrorResponse> handleKakaoNameExceptions(KakaoNameException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = makeErrorResponse(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();
    }
}
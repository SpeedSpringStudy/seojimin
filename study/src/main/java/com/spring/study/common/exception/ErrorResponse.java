package com.spring.study.common.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        String code,
        String message
) {
    public ErrorResponse(ErrorCode errorCode){
        this(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}

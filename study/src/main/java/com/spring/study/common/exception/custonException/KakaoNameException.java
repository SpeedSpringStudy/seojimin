package com.spring.study.common.exception.custonException;

import com.spring.study.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class KakaoNameException extends RuntimeException{

    private final ErrorCode errorCode;

    public KakaoNameException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

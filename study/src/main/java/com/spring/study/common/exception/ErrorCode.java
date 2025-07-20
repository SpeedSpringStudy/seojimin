package com.spring.study.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
    INPUT_INVALID_VALUE(409, "G002", "잘못된 입력"),
    ACCESS_INVALID_VALUE(400, "G003", "잘못된 접근"),


    // Product
    KAKAO_NAME_CONTAIN(400, "P001", "KAKAO가 들어간 상품명은 MD와 협의 후 사용 가능")

    ;
    private final int status;
    private final String code;
    private final String message;
}

package com.spring.study.domain.dto.request;

import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.KakaoNameException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 상품 추가, 수정 시 유효성 검사 -[ ]
 * 상품 이름은 공백 포함 최대 15자까지 입력 가능 -[ ]
 * 가능한 특수 문자: ( ), [ ], +, -, &, /, _ -[ ]
 * "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능
 */
public record ProductRequest(
        @NotNull(message = "상품 이름은 필수입니다.")
        @Size(max = 15, message = "상품 이름은 공백 포함 최대 15자까지 입력 가능합니다.")
        @Pattern(regexp = "^[A-Za-z0-9()\\[\\]+\\-&/_ ]+$")
        String name,
        int price
) {
        public ProductRequest {
                if (name.contains("카카오")) {
                        throw new KakaoNameException(ErrorCode.KAKAO_NAME_CONTAIN);
                }
        }
}
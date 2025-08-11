package com.spring.study.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfo {
    private Long id;
    private Map<String, Object> properties;

    @JsonProperty("kakao_account")
    private Map<String, Object> kakaoAccount;
}
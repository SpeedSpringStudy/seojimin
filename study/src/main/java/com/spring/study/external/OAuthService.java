package com.spring.study.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.study.common.auth.jwt.JwtUtil;
import com.spring.study.common.auth.jwt.TokenTypes;
import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.BusinessException;
import com.spring.study.domain.dto.response.LoginResponse;
import com.spring.study.domain.entity.Member;
import com.spring.study.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final KakaoProperties kakaoProperties;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse kakaoLogin(String accessToken, HttpServletResponse response){
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(accessToken);
        Long kakaoId = kakaoUserInfo.getId();
        Map<String, Object> kakaoAccount = kakaoUserInfo.getKakaoAccount();
        for (String s : kakaoAccount.keySet()) {
            System.out.println("key: "+s +" " + kakaoAccount.get(s));
        }
        String email = (String) kakaoAccount.get("email");
        Member targetMember = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    // 없으면 회원가입 처리
                    Member newMember = Member.builder()
                            .email(email)
                            .oauthId(kakaoId)
                            .password(encoder.encode("password"))
                            .build();
                    return memberRepository.save(newMember);
                });

        // 토큰 발급
        String access = jwtUtil.createJwt(TokenTypes.ACCESS.getType(), targetMember.getEmail(), targetMember.getId());
        String refresh = jwtUtil.createJwt(TokenTypes.REFRESH.getType(), targetMember.getEmail(), targetMember.getId());

        // 헤더에 엑세스 토큰 추가
        response.addHeader("Authorization", "Bearer " + access);
        response.addHeader(HttpHeaders.SET_COOKIE, createCookie(refresh).toString());

        return new LoginResponse(targetMember.id, targetMember.email);
    }

    public KakaoUserInfo getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                KakaoUserInfo.class
        );

        return response.getBody();
    }

    public String getAccessToken(String authorizationCode) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoProperties.getClientId());
        body.add("redirect_uri", kakaoProperties.getRedirectUri());
        body.add("code", authorizationCode);

        RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(
                body,
                headers,
                HttpMethod.POST,
                URI.create(url)
        );

        ResponseEntity<String> response = restTemplate
                .postForEntity(kakaoProperties.getTokenUrl(), request, String.class);

        return extractAccessToken(response.getBody());
    }

    private String extractAccessToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseCookie createCookie(String value) {
        return ResponseCookie.from(TokenTypes.REFRESH.getType(), value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("None")
                .build();
    }
}

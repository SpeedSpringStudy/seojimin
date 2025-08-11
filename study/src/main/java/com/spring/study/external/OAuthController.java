package com.spring.study.external;

import com.spring.study.domain.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;
    private final KakaoProperties kakaoProperties;

    @GetMapping("/kakao")
    public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
        String kakaoLoginUrl = kakaoProperties.getAuthUrl()
                + "?response_type=code"
                + "&client_id=" + kakaoProperties.getClientId()
                + "&redirect_uri=" + kakaoProperties.getRedirectUri();

        response.sendRedirect(kakaoLoginUrl);
    }

    @GetMapping("/login/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {
        String accessToken = oAuthService.getAccessToken(code);
        return ResponseEntity.ok().body(oAuthService.kakaoLogin(accessToken, response));
    }

}
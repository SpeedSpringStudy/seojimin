package com.spring.study.common.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.NotFoundException;
import com.spring.study.domain.entity.Member;
import com.spring.study.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 접근 권한 허용 경로 검증
        if (isAllowedRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header에서 accessToken 추출
        String tokenHeader = request.getHeader("Authorization");

        // Bearer 시작 여부 및 null 값 검증
        if (tokenHeader==null || !tokenHeader.startsWith("Bearer ")) {
            log.info("request URI : {}", request.getRequestURI());
            log.info("Invalid or missing access-token");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = tokenHeader.substring(7);

        // accessToken 만료시간 검증
        if (jwtUtil.isExpired(accessToken)) {
            String refreshToken = getRefreshTokenFromCookie(request);

            if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
                String email = jwtUtil.getEmail(refreshToken);
                Member member = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

                if (refreshToken.equals(member.getRefreshToken())) {

                    // 새 토큰 발급
                    String newAccessToken = jwtUtil.createJwt("access", email, member.getId());
                    String newRefreshToken = jwtUtil.createJwt("refresh", email, member.getId());

                    member.updateRefreshToken(newRefreshToken);

                    // 헤더로 응답
                    response.setHeader("Authorization", "Bearer " + newAccessToken);

                    // 쿠키로 refreshToken 전달
                    ResponseCookie refreshCookie = ResponseCookie.from("refresh-token", newRefreshToken)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(14 * 24 * 60 * 60)
                            .sameSite("None")
                            .build();
                    response.setHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

                    // SecurityContext 갱신
                    setAuthentication(newAccessToken);

                    filterChain.doFilter(request, response);
                    return;
                }
            }

            setResponse(response);
            return;
        }

        setAuthentication(accessToken);
        filterChain.doFilter(request, response);
    }

    // 접근 권한 허용 경로 검증 (true -> 통과, false -> 예외)
    private boolean isAllowedRequest(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String path = request.getRequestURI();

        // 1. Options 요청 검증
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 2. 예외 경로 검증
        return isExcludedPath(pathMatcher, path);
    }

    // email을 추출 및 인증 정보를 설정
    private void setAuthentication(String token) {
        String email = jwtUtil.getEmail(token);
        Authentication authentication = jwtUtil.getAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (TokenTypes.REFRESH.getType().equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    // Access-Token 만료시 401 반환
    private void setResponse(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> body = Map.of("message", "accessToken 만료");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }

    // Jwt 검증 제외 경로
    private boolean isExcludedPath(AntPathMatcher pathMatcher, String requestURI) {

        return pathMatcher.match("/api/members/login", requestURI)
                || pathMatcher.match("/api/members/signup", requestURI)
                || pathMatcher.match("/products/*", requestURI)
                || pathMatcher.match("/products/**", requestURI)
                || pathMatcher.match("/login", requestURI)
                || pathMatcher.match("/signup", requestURI)
                || pathMatcher.match("/api/products", requestURI)
                || pathMatcher.match("/oauth/*", requestURI)
                || pathMatcher.match("/oauth/**", requestURI)
                || pathMatcher.match("/oauth", requestURI);

    }
}

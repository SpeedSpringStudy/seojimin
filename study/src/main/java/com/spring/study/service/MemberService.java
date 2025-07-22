package com.spring.study.service;

import com.spring.study.domain.dto.request.memberRequest;
import com.spring.study.domain.dto.response.LoginResponse;
import com.spring.study.domain.entity.Member;
import com.spring.study.common.auth.jwt.JwtUtil;
import com.spring.study.common.auth.jwt.TokenTypes;
import com.spring.study.repository.dao.MemberDao;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;
    private final JwtUtil jwtUtil;

    public LoginResponse login(memberRequest request, HttpServletResponse response){
        Member targetMember = memberDao.findByEmail(request.email());
        if (!targetMember.getPassword().equals(request.password())){
            throw new IllegalArgumentException();
        }

        // 토큰 발급
        String access = jwtUtil.createJwt(TokenTypes.ACCESS.getType(), targetMember.getEmail(), targetMember.getId());
        String refresh = jwtUtil.createJwt(TokenTypes.REFRESH.getType(), targetMember.getEmail(), targetMember.getId());

        // 헤더에 엑세스 토큰 추가
        response.addHeader("Authorization", "Bearer " + access);
        response.addHeader(HttpHeaders.SET_COOKIE, createCookie(refresh).toString());

        // 리프레시 토큰 저장
        targetMember.setRefreshToken(refresh);
        memberDao.updateRefreshToken(targetMember.getId(), refresh);

        return new LoginResponse(targetMember.getId(), targetMember.getEmail());
    }

    public void signUp(memberRequest request) {
        memberDao.signUp(request.email(), request.password());
    }

    public Member getMemberByAuthentication() {
        // Authentication 에서 추출한 이메일로 사용자 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();
        return memberDao.findByEmail(memberEmail);
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

package com.spring.study.service;

import com.spring.study.domain.dto.request.memberRequest;
import com.spring.study.domain.dto.response.LoginResponse;
import com.spring.study.domain.entity.Member;
import com.spring.study.jwt.JwtUtil;
import com.spring.study.jwt.TokenTypes;
import com.spring.study.repository.dao.MemberDao;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

        // 리프레시 토큰 저장
        targetMember.setRefreshToken(refresh);
        memberDao.updateRefreshToken(targetMember.getId(), refresh);

        return new LoginResponse(targetMember.getId(), targetMember.getEmail());
    }

    public void signUp(memberRequest request) {
        memberDao.signUp(request.email(), request.password());
    }
}

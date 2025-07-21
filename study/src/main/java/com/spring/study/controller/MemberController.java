package com.spring.study.controller;

import com.spring.study.domain.dto.request.memberRequest;
import com.spring.study.domain.dto.response.LoginResponse;
import com.spring.study.domain.entity.Member;
import com.spring.study.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody memberRequest request){
        memberService.signUp(request);
        return new ResponseEntity<>("회원가입에 성공하였습니다.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody memberRequest request, HttpServletResponse response){
        LoginResponse loginResponse = memberService.login(request, response);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}

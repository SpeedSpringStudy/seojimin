package com.spring.study.common.auth;

import com.spring.study.domain.entity.Member;
import com.spring.study.repository.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberDao memberDao;

    // 사용자 인증을 위한 사용자 정보 로드 클래스
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member foundMember = memberDao.findByEmail(email);

        return new CustomUserDetails(foundMember);
    }
}

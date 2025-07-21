package com.spring.study.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secretKey}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm());
    }

    // 토큰 검증(만료기간)
    public boolean isExpired(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();

            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // 토큰 생성
    public String createJwt(String type, String email, Long memberIdentity) {

        log.info("createJwt - type: {}, email: {}, memberIdentity: {}", type, email, memberIdentity);

        long expirationTime = "access".equals(type)
                ? accessTokenExpirationPeriod
                : refreshTokenExpirationPeriod;

        return Jwts.builder()
                .claim("type", type)
                .claim("email", email)
                .claim("memberIdentity", memberIdentity)
                .notBefore(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }
}

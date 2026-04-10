package com.github.scproject1.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTToken {
    private final Set<String> blackList = Collections.synchronizedSet(new HashSet<>());

    private Key key;
    private final long tokenValidityInMilliseconds;

    public JWTToken(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long tokenValidityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    // 토큰 생성 메서드
    public String createToken(String email) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(email) // 토큰에 담을 정보 (유저 이메일)
                .setIssuedAt(new Date())
                .setExpiration(validity) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("토큰 검증 실패 원인: " + e.getMessage());
            return false;
        }
    }

    // 토큰에서 인증 정보(Authentication) 가져오기
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();


        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                claims.getSubject(),
                "",
                Collections.emptyList()
        );
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 로그아웃
    public void logout(String token) {
        blackList.add(token);
    }

    public boolean isLoggedOut(String token) {
        return blackList.contains(token);
    }

}

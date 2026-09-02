package com.example.ticketing.global.security;

import com.example.ticketing.member.domain.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/**
 * JwtToken 설정
 * key: 토큰 서명, 검증에 쓰는 비밀키 객체
 * expirationSeconds: 토큰 유효기간(초)
 */
@Component
public class JwtProvider {
    private final SecretKey key;
    private final long expirationSeconds;

    // Application.yml 의 jwt.secret, jwt.expiration-seconds 각각 문자열, 숫자로 주입받습니다.
    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration-seconds}") long expirationSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // 최소 32바이트(256비트), 미만시 예외
        this.expirationSeconds = expirationSeconds;
    }

    // 토큰 생성
    public String createToken(Long memberId, MemberRole role) {
        Instant now = Instant.now(); // 현재 시각

        return Jwts.builder()
                .subject(String.valueOf(memberId)) // JWT 표준 클레임 sub에 회원 ID 등록
                .claim("role", role.name()) // 커스텀 클레임 role.name() >> "ADMIN"
                .issuedAt(Date.from(now)) // 표준 클레임 iat(issued at, 발급시각) JWT API가 java.util.Date를 받아
                .expiration(Date.from(now.plusSeconds(expirationSeconds))) // 표준 클레임 exp(만료시각): 지금 + 유효기간(초) 계산후 Date로 변환.
                .signWith(key) // header + payload를 key로 서명한다.(위변조 여부 검증)
                .compact(); // 빌더의 내용을 최종 문자열 하나로 Serialize 헤더.페이로드.서명 형태의 xxxx.yyyy.zzzz JWT 문자열 반환
    }

    // 토큰 검증 및 해석
    public Claims parse(String token) {
        return Jwts.parser() // 토큰을 읽는 파서 빌더 생성
                .verifyWith(key) // 검증에 사용할 키를 지정 (서명 불일치 예외 발생 가능)
                .build() // 설정이 끝난 파서 객체 완성
                .parseSignedClaims(token) // 전달받은 토큰 문자열을 실제로 파싱한다. 이때 1. 서명 검증 실패, 2. 만료(exp) 3. 형식 등 오류 각각 예외를 던짐
                .getPayload(); // 결과 객체에서 payload 부분 즉, Claims(...Claim)을 꺼내 호출부에서는 claims.getSubject() > memberId etc
    }
}

package com.example.ticketing.global.security;

import com.example.ticketing.member.domain.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 스프링도 DB도 띄우지 않는다. JwtProvider는 생성자만 있으면 되는 평범한 객체다.
 */
class JwtProviderTest {

    private static final String SECRET = "test-secret-key-must-be-at-least-32-bytes!";

    private final JwtProvider jwtProvider = new JwtProvider(SECRET, 3600);

    @Test
    @DisplayName("만든 토큰을 다시 파싱하면 회원 id와 역할이 그대로 나온다")
    void createAndParse() {
        // given
        String token = jwtProvider.createToken(1L, MemberRole.USER);

        // when
        Claims claims = jwtProvider.parse(token);

        // then
        assertThat(claims.getSubject()).isEqualTo("1");
        assertThat(claims.get("role", String.class)).isEqualTo("USER");
        assertThat(claims.getExpiration()).isAfter(claims.getIssuedAt());
    }

    @Test
    @DisplayName("만료된 토큰은 거부한다")
    void expiredToken() {
        // given - 만료시각이 1분 전인 토큰
        JwtProvider expiredProvider = new JwtProvider(SECRET, -60);
        String token = expiredProvider.createToken(1L, MemberRole.USER);

        // when / then
        assertThatThrownBy(() -> expiredProvider.parse(token))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    @DisplayName("다른 키로 서명된 토큰은 거부한다")
    void tamperedToken() {
        // given - 공격자가 자기 키로 ADMIN 토큰을 만들었다
        JwtProvider attacker = new JwtProvider("another-secret-key-at-least-32-bytes-long!", 3600);
        String token = attacker.createToken(1L, MemberRole.ADMIN);

        // when / then - 우리 키로는 서명 검증이 실패한다
        assertThatThrownBy(() -> jwtProvider.parse(token))
                .isInstanceOf(SignatureException.class);
    }
}

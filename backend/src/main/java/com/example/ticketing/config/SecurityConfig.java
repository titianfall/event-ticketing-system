package com.example.ticketing.config;

import com.example.ticketing.global.security.JwtAuthenticationFilter;
import com.example.ticketing.global.security.JwtProvider;
import com.example.ticketing.global.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 시큐리티 필터 체인.
 *
 * SecurityFilterChain 빈을 직접 정의하면 부트 기본 설정(전 경로 인증 + 랜덤 비밀번호)이
 * 꺼지고 여기 적은 것만 적용된다. formLogin/httpBasic은 호출하지 않았으므로 붙지 않는다.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 토큰 인증이라 세션/쿠키를 안 쓴다. CSRF 토큰도 필요 없다.
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/health").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/members/signup", "/api/auth/login").permitAll()
                        .anyRequest().authenticated())

                // 아이디/비밀번호 폼 인증 자리에 JWT 검증을 끼워 넣는다
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)

                // 인증 실패 응답을 공통 실패 형식으로
                .exceptionHandling(handling ->
                        handling.authenticationEntryPoint(authenticationEntryPoint))

                .build();
    }
}

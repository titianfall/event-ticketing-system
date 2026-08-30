package com.example.ticketing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 비밀번호 해시용 인코더 빈
 * spring-security-crypto 모듈 사용 / security-filter-chain 은 아직 없음
 *
 * createDelegatingPasswordEncoder()는 "{bcrypt}$2a$..." 형태로 저장한다.
 * 접두사 덕분에 나중에 알고리즘을 바꿔도 기존 해시를 계속 검증할 수 있다.
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

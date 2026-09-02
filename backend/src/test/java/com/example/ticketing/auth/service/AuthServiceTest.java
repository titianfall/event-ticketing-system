package com.example.ticketing.auth.service;

import com.example.ticketing.auth.dto.LoginRequest;
import com.example.ticketing.auth.dto.LoginResponse;
import com.example.ticketing.global.response.BusinessException;
import com.example.ticketing.global.response.ErrorCode;
import com.example.ticketing.global.security.JwtProvider;
import com.example.ticketing.member.domain.Member;
import com.example.ticketing.member.domain.MemberRole;
import com.example.ticketing.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * AuthService 단위 테스트. 스프링 컨텍스트도 DB도 띄우지 않는다.
 * 리포지토리, 인코더, 토큰 발급기는 목으로 대체한다.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private MemberRepository memberRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtProvider jwtProvider;
    @InjectMocks private AuthService authService;

    private final LoginRequest request = new LoginRequest("a@test.com", "password1");

    /** 저장된 회원을 흉내낸다. id는 JPA가 채우는 값이라 리플렉션으로 넣는다. */
    private Member savedMember() {
        Member member = Member.createUser("홍길동", "a@test.com", "ENCODED_PW");
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    @Test
    @DisplayName("이메일과 비밀번호가 맞으면 Bearer 토큰을 반환한다")
    void loginSuccess() {
        // given
        given(memberRepository.findByEmail("a@test.com")).willReturn(Optional.of(savedMember()));
        given(passwordEncoder.matches("password1", "ENCODED_PW")).willReturn(true);
        given(jwtProvider.createToken(1L, MemberRole.USER)).willReturn("TOKEN");

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(response.accessToken()).isEqualTo("TOKEN");
        assertThat(response.tokenType()).isEqualTo("Bearer");
    }

    @Test
    @DisplayName("가입되지 않은 이메일이면 LOGIN_FAILED를 던지고 토큰을 만들지 않는다")
    void loginUnknownEmail() {
        // given
        given(memberRepository.findByEmail("a@test.com")).willReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.LOGIN_FAILED);

        verify(jwtProvider, never()).createToken(any(), any());
    }

    @Test
    @DisplayName("비밀번호가 틀리면 LOGIN_FAILED를 던지고 토큰을 만들지 않는다")
    void loginWrongPassword() {
        // given
        given(memberRepository.findByEmail("a@test.com")).willReturn(Optional.of(savedMember()));
        given(passwordEncoder.matches("password1", "ENCODED_PW")).willReturn(false);

        // when / then - 없는 이메일과 같은 에러 코드다. 계정 존재 여부를 노출하지 않는다.
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.LOGIN_FAILED);

        verify(jwtProvider, never()).createToken(any(), any());
    }
}

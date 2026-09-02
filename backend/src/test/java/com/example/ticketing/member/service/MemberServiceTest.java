package com.example.ticketing.member.service;

import com.example.ticketing.global.response.BusinessException;
import com.example.ticketing.global.response.ErrorCode;
import com.example.ticketing.member.domain.Member;
import com.example.ticketing.member.domain.MemberRole;
import com.example.ticketing.member.dto.SignupRequest;
import com.example.ticketing.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * MemberService 단위 테스트. 스프링 컨텍스트도 DB도 띄우지 않는다.
 * 리포지토리와 인코더는 목으로 대체한다.
 */
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock private MemberRepository memberRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private MemberService memberService;

    private final SignupRequest request = new SignupRequest("a@test.com", "password1", "홍길동");

    @Test
    @DisplayName("가입에 성공하면 인코딩된 비밀번호로 USER 회원을 저장하고 id를 반환한다")
    void signupSuccess() {
        // given - 응답을 조작
        given(memberRepository.existsByEmail("a@test.com")).willReturn(false);
        given(passwordEncoder.encode("password1")).willReturn("ENCODED_PW");
        given(memberRepository.saveAndFlush(any(Member.class))).willAnswer(invocation -> {
            Member saved = invocation.getArgument(0);
            ReflectionTestUtils.setField(saved, "id", 1L); // 저장후 반환된 id를 흉내
            return saved;
        });

        // when - 회원등록시
        Long memberId = memberService.signup(request);

        // then - "홍길동", "a@test.com", "USER", "ENCODED_PW" 확인
        assertThat(memberId).isEqualTo(1L);

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).saveAndFlush(captor.capture());
        Member persisted = captor.getValue();

        assertThat(persisted.getName()).isEqualTo("홍길동");
        assertThat(persisted.getRole()).isEqualTo(MemberRole.USER);
        assertThat(persisted.getEmail()).isEqualTo("a@test.com");

        assertThat(persisted.getPassword()).isEqualTo("ENCODED_PW");
    }

    @Test
    @DisplayName("이미 가입된 이메일이라면 저장하지 않고 DUPLICATE_EMAIL 예외를 던진다")
    void signupDuplicateEmail() {
        // given - a@test.com 이라는 이메일을 조회하면 true(이미 가입된 상태)로 만듭니다.
        given(memberRepository.existsByEmail("a@test.com")).willReturn(true);

        // when / then
        assertThatThrownBy(() -> memberService.signup(request))
                // 발생한 예외가 BusinessException예외인지 확인합니다. (Not NPE)
                .isInstanceOf(BusinessException.class)
                // 붙잡은 예외에서 errorCode 프로퍼티를 꺼냅니다.
                .extracting("errorCode") // AssertJ(리플렉션) > @Getter > errorCode
                .isEqualTo(ErrorCode.DUPLICATE_EMAIL);

        // 검증 - 앞서 중복호출로 비즈니스 예외가 불리며 encode도 saveAndFlush도 불리지 않고 에러가 호출되었다.
        // 1. saveAndFlush는 안불렸는가?
        // 2. encode는 안불렸는가?
        verify(memberRepository, never()).saveAndFlush(any());
        verify(passwordEncoder, never()).encode(any());
    }
}
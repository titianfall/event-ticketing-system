package com.example.ticketing.member.controller;

import com.example.ticketing.global.security.JwtProvider;
import com.example.ticketing.member.domain.Member;
import com.example.ticketing.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 시큐리티 필터 체인까지 태우는 통합 테스트. 로컬 PostgreSQL이 떠 있어야 한다.
 *
 * 여기서만 확인할 수 있는 것:
 *  - 필터 단계 401이 공통 실패 형식(ApiResponse)으로 나가는가 (EntryPoint)
 *  - 유효한 토큰의 principal이 컨트롤러까지 전달되는가
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional // 테스트마다 롤백
class MemberMeIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtProvider jwtProvider;

    @Test
    @DisplayName("토큰 없이 호출하면 401을 공통 실패 형식으로 반환한다")
    void withoutToken() throws Exception {
        mockMvc.perform(get("/api/members/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value("인증이 필요합니다"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("잘못된 토큰도 401로 거부한다")
    void withInvalidToken() throws Exception {
        mockMvc.perform(get("/api/members/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer this.is.not.a.jwt"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"));
    }

    @Test
    @DisplayName("유효한 토큰이면 내 정보를 반환한다")
    void withValidToken() throws Exception {
        // given
        Member member = memberRepository.save(
                Member.createUser("홍길동", "me@test.com", passwordEncoder.encode("password1")));
        String token = jwtProvider.createToken(member.getId(), member.getRole());

        // when / then
        mockMvc.perform(get("/api/members/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data.email").value("me@test.com"))
                .andExpect(jsonPath("$.data.name").value("홍길동"))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.password").doesNotExist()); // 해시를 노출하지 않는다
    }

    @Test
    @DisplayName("헬스 체크는 인증 없이도 열려 있다")
    void healthIsPublic() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"));
    }
}

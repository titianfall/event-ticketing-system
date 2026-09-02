package com.example.ticketing.member.controller;

import com.example.ticketing.global.response.BusinessException;
import com.example.ticketing.global.response.ErrorCode;
import com.example.ticketing.member.dto.SignupRequest;
import com.example.ticketing.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MemberController 슬라이스 테스트
 * 웹 계층(+ GlobalExceptionhandler)만 로드하고 MemberService는 목으로 넣는다.
 * spring-security-crypto만 있고 시큐리티 필터 체인은 없으므로 addFilters 조정이 필요 없다.
 */
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockitoBean private MemberService memberService;

    @Test
    @DisplayName("정상 201, memberId를 반환")
    void signupSuccess() throws Exception{
        // given
        given(memberService.signup(any(SignupRequest.class))).willReturn(1L);
        String body = objectMapper.writeValueAsString(
                new SignupRequest("a@test.com", "password1", "홍길동")
        );

        // when/then
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data.memberId").value(1));
    }

    @Test
    @DisplayName("이메일, 비밀번호 입력 형식 400 INVALID_INPUT을 반환")
    void signupInvaildInput() throws Exception {
        // given
        String body = objectMapper.writeValueAsString(
                new SignupRequest("not-an-email", "short", "")
        );

        // when/then
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.message").value("email: 이메일 형식이 올바르지 않습니다., name: 이름은 필수입니다, password: 비밀번호는 8자 이상 64자 이하여야 합니다"));
    }

    @Test
    @DisplayName("중복 이메일 예외 409 DUPLICATIE_EMAIL")
    void signupDuplicateEamil() throws Exception {
        // given
        given(memberService.signup(any(SignupRequest.class)))
                .willThrow(new BusinessException(ErrorCode.DUPLICATE_EMAIL));
        String body = objectMapper.writeValueAsString(
                new SignupRequest("a@test.com", "password1", "홍길동")
        );

        // when/then
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_EMAIL"))
                .andExpect(jsonPath("$.message").value("이미 가입된 이메일입니다"));
    }
}
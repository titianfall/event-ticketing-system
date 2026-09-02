package com.example.ticketing.auth.controller;

import com.example.ticketing.auth.dto.LoginRequest;
import com.example.ticketing.auth.dto.LoginResponse;
import com.example.ticketing.auth.service.AuthService;
import com.example.ticketing.global.response.BusinessException;
import com.example.ticketing.global.response.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AuthController 슬라이스 테스트.
 * 웹 계층(+ GlobalExceptionHandler)만 로드하고 AuthService는 목으로 넣는다.
 * addFilters = false 로 시큐리티 필터를 빼서 컨트롤러 로직만 본다.
 * (필터를 태우는 검증은 MemberMeIntegrationTest가 담당)
 */
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockitoBean private AuthService authService;

    @Test
    @DisplayName("정상 로그인이면 200과 accessToken을 반환한다")
    void loginSuccess() throws Exception {
        // given
        given(authService.login(any(LoginRequest.class))).willReturn(LoginResponse.bearer("TOKEN"));
        String body = objectMapper.writeValueAsString(new LoginRequest("a@test.com", "password1"));

        // when / then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data.accessToken").value("TOKEN"))
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"));
    }

    @Test
    @DisplayName("로그인 실패는 401 LOGIN_FAILED를 반환한다")
    void loginFailed() throws Exception {
        // given
        given(authService.login(any(LoginRequest.class)))
                .willThrow(new BusinessException(ErrorCode.LOGIN_FAILED));
        String body = objectMapper.writeValueAsString(new LoginRequest("a@test.com", "wrongpassword"));

        // when / then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("LOGIN_FAILED"))
                .andExpect(jsonPath("$.message").value("이메일 또는 비밀번호가 올바르지 않습니다"));
    }

    @Test
    @DisplayName("이메일 형식이 틀리거나 비밀번호가 비면 400 INVALID_INPUT을 반환한다")
    void loginInvalidInput() throws Exception {
        // given
        String body = objectMapper.writeValueAsString(new LoginRequest("not-an-email", ""));

        // when / then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}

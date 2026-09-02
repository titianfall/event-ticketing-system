package com.example.ticketing.auth.dto;

/**
 * 로그인 성공 응답 본문
 * ApiResponse의 data 자리에 들어간다.
 * {"code": "SUCCESS", "message": null, "data": {"accessToken":"eyj...", "tokenType":"Bearer"}}
 */
public record LoginResponse(String accessToken, String tokenType) {
    public static LoginResponse bearer(String accessToken) {
        return new LoginResponse(accessToken, "Bearer");
    }
}

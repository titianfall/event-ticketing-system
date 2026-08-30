package com.example.ticketing.member.dto;

/**
 * 회원가입 성공 응답 본문
 * ApiResponse의 data 자리에 들어간다.
 * {"code":"SUCCESS", "message": null, "data": {"memberId": 1}}
 *
 * 비밀번호나 해시는 담지 않는다.
 */
public record SignupResponse(Long memberId) {
}

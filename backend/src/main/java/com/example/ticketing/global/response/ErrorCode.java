package com.example.ticketing.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드와 HTTP 상태, 기본 메시지를 묶어 둔다.
 * 도메인이 생길 때마다 한 줄씩 추가한다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 - BAD_REQUEST(CLIENT ERROR)
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
    // 500 - INTERNAL_SERVER_ERROR(SERVER_ERROR)
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다"),
    // 409 - CONFLICT(요청은 형식상 맞지만 현재 상태(이미 있는 이메일)과 출동한다.
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다");

    private final HttpStatus status;
    private final String message;
}

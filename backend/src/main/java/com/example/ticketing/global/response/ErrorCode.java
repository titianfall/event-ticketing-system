package com.example.ticketing.global.response;

import org.springframework.http.HttpStatus;

/**
 * 에러 코드와 HTTP 상태, 기본 메시지를 묶어 둔다.
 * 도메인이 생길 때마다 한 줄씩 추가한다.
 */
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

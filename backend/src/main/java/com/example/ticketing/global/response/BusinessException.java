package com.example.ticketing.global.response;

import lombok.Getter;

/**
 * 서비스가 의도적으로 던지는 예외.
 * ErrorCode를 실어 보내면 GlobalExceptionHandler가 응답으로 바꾼다.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

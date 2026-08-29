package com.example.ticketing.global.response;

/**
 * 모든 API가 쓰는 공통 응답 형식.
 * record라 getter가 code(), message(), data()다.
 */
public record ApiResponse<T>(String code, String message, T data) {

    private static final String SUCCESS_CODE = "SUCCESS";

    /** 성공 응답. data 타입은 호출하는 쪽에서 정해진다. */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_CODE, null, data);
    }

    /** 실패 응답. errorCode.name()이 "INVALID_INPUT" 같은 코드 문자열이 된다. */
    public static ApiResponse<Void> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.name(), errorCode.getMessage(), null);
    }

    /** 기본 메시지 대신 상황에 맞는 메시지를 담을 때 쓴다. */
    public static ApiResponse<Void> fail(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.name(), message, null);
    }
}

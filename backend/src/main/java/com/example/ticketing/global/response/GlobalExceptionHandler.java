package com.example.ticketing.global.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 컨트롤러에서 터진 예외를 ApiResponse 실패 응답으로 바꾼다.
 * 컨트롤러 이전 단계(Security 필터 등)의 예외는 여기로 오지 않는다.
 */
@Slf4j // log 필드를 만들어 준다
@RestControllerAdvice // 모든 컨트롤러의 예외를 여기서 잡아 JSON으로 응답한다
public class GlobalExceptionHandler {

    /** 서비스가 의도적으로 던진 예외. 상태와 메시지는 ErrorCode가 들고 있다. */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus()) // ErrorCode가 들고 있는 HTTP 상태
                .body(ApiResponse.fail(errorCode)); // 본문은 공통 실패 형식
    }

    /** 실패한 필드들을 한 줄로 합친다. 예: "email: 형식 오류, password: 8자 이상" */
    private String toMessage(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                // 필드 하나를 "이름: 메시지" 문자열로 바꾼다
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", ")); // 쉼표로 이어 붙인다
    }

    /** @Valid 검증 실패. 어느 필드가 왜 틀렸는지는 기본 메시지 대신 따로 담는다. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
        String message = toMessage(e.getBindingResult().getFieldErrors());

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponse.fail(ErrorCode.INVALID_INPUT, message));
    }

    /** 그물망. 원인은 로그에만 남기고 응답에는 내부 메시지를 넣지 않는다. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception e) {
        log.error("처리하지 못한 예외", e);

        return ResponseEntity
                .status(ErrorCode.INTERNAL_ERROR.getStatus())
                .body(ApiResponse.fail(ErrorCode.INTERNAL_ERROR));
    }
}

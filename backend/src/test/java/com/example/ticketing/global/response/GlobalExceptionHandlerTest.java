package com.example.ticketing.global.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * GlobalExceptionHandler는 Spring 없이도 동작하는 평범한 객체다.
 * 컨텍스트를 띄우지 않고 직접 호출해서 검증한다.
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("BusinessException은 ErrorCode의 상태와 코드로 응답된다")
    void businessExceptionToResponse() {
        // given
        BusinessException exception = new BusinessException(ErrorCode.INVALID_INPUT);

        // when
        ResponseEntity<ApiResponse<Void>> response = handler.handleBusiness(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("INVALID_INPUT");
        assertThat(response.getBody().message()).isEqualTo("입력값이 올바르지 않습니다");
        assertThat(response.getBody().data()).isNull();
    }

    @Test
    @DisplayName("예상하지 못한 예외는 내부 메시지를 응답에 노출하지 않는다")
    void unexpectedExceptionHidesInternalMessage() {
        // given
        RuntimeException exception = new RuntimeException("column \"password\" does not exist");

        // when
        ResponseEntity<ApiResponse<Void>> response = handler.handleUnexpected(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("INTERNAL_ERROR");
        assertThat(response.getBody().message()).doesNotContain("password");
    }
}

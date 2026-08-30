package com.example.ticketing.member.controller;

import com.example.ticketing.global.response.ApiResponse;
import com.example.ticketing.member.dto.SignupRequest;
import com.example.ticketing.member.dto.SignupResponse;
import com.example.ticketing.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 관련 HTTP 엔드포인트
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입. 성공하면 201 Created와 생성된 회원 id를 돌려준다.
     * @Valid 실패 -> MethodArgumentNotValidException -> GlobalExceptionHandler에서 400 INVALID_INPUT
     * 중복 이메일 -> BusinessException(DUPLICATE_EMAIL) -> GlobalExceptionhandler에서 409 CONFLICT
     *
     * 성공시: {"code": "SUCCESS", "message": null, "data": {"memberId": 1}}
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        Long memberId = memberService.signup(request);
        return ApiResponse.success(new SignupResponse(memberId));
    }
}

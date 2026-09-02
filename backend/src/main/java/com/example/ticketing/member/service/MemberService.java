package com.example.ticketing.member.service;

import com.example.ticketing.global.response.BusinessException;
import com.example.ticketing.global.response.ErrorCode;
import com.example.ticketing.member.domain.Member;
import com.example.ticketing.member.domain.MemberRole;
import com.example.ticketing.member.dto.MemberResponse;
import com.example.ticketing.member.dto.SignupRequest;
import com.example.ticketing.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ErrorCode.DUPLICATE_EMAIL: 409
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 서비스
     * 중복 이메일을 막는 2단계
     * 1. existsByEmail 선검사 - 사용자 친화적인 메시지 출력
     * 2. DB Unique 제약 - DataIntegrityViolationException을 BusinessException으로 변환
     *      saveAndFlush가 DataIntegrityViolationException을 던짐
     */
    @Transactional
    public Long signup(SignupRequest request) {
        if(memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
        String encoded = passwordEncoder.encode(request.password());
        Member member = Member.createUser(request.name(), request.email(), encoded);

        try{
            return memberRepository.saveAndFlush(member).getId();
        } catch(DataIntegrityViolationException e) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    /**
     * 내 정보 조회.
     * 토큰은 유효한데 그 사이 회원이 지워졌다면 MEMBER_NOT_FOUND(404).
     */
    @Transactional(readOnly = true)
    public MemberResponse findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberResponse.from(member);
    }
}

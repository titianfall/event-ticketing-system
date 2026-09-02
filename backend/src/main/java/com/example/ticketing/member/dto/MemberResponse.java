package com.example.ticketing.member.dto;

import com.example.ticketing.member.domain.Member;
import com.example.ticketing.member.domain.MemberRole;

/**
 * 내 정보 응답 본문.
 * ApiResponse의 data 자리에 들어간다.
 * {"code":"SUCCESS", "message": null, "data": {"id":1, "email":"a@test.com", "name":"홍길동", "role":"USER"}}
 *
 * 비밀번호나 해시는 담지 않는다.
 */
public record MemberResponse(Long id, String email, String name, MemberRole role) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getName(), member.getRole());
    }
}

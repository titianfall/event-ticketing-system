package com.example.ticketing.member.repository;

import com.example.ticketing.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 회원 이메일을 등록할경우에 Race Condition 방지가 필요하다.
     * 검사 및 저장은 원자적인 작업이 아니기 때문에 중복을 막지 못한다.
     * ex)
     * reqA: existsByEmail("A@test.com") -> false
     * reqB: existsByEmail("A@test.com") -> false
     * reqA: 회원 저장
     * reqB: 회원 저장
     *
     * 때문에
     * 사용자: 친화적인 오류
     * 보장: email(DB Unique Constraint)
     * 해야한다.
     */
    // 1. 회원 이메일(unique, notnull)  Optional 처리
    // 2. 회원 이메일 존재 여부 반환용
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

}

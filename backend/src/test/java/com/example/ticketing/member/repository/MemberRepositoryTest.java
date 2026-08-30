package com.example.ticketing.member.repository;

import com.example.ticketing.member.domain.Member;
import com.example.ticketing.member.domain.MemberRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    void 이메일로_회원을_조회한다() {
        //given
        Member memberA = new Member("memberA", "a@test.com", "1234", MemberRole.USER);
        memberRepository.save(memberA);

        //when
        // Optional.get() >> NoSuchElementException발생 가능
        Optional<Member> result = memberRepository.findByEmail(memberA.getEmail());
        assertThat(result).isPresent();
        /**
         * Member findMember = memberRepository.findByEmali("a@test.com")
         *      .orElseThrow(() -> new AssertionError("회원 조회 실패"));
         */

        //then
        Member findMember = result.orElseThrow();
        assertThat(findMember.getName()).isEqualTo("memberA");
        assertThat(findMember.getEmail()).isEqualTo("a@test.com");
    }
}
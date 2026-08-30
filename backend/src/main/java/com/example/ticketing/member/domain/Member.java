package com.example.ticketing.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 프록시용
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"})
)
@ToString(of = {"id", "name", "email", "role", "createdAt"}) // password 해시는 로그에 남기지 않는다
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id") // 엔티티 id는 entity_id 형태로 고정
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String name; // or nickname

    @NotBlank
    @Column(nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING) // 숫자로 하게되면 타입 추가시 번호가 밀릴수있음
    @Column(nullable = false)
    private MemberRole role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Member(String name, String email, String password, MemberRole role) {
        // super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * 일반 회원 가입용 - 역할 USER 고정
     * password는 반드시 인코딩된 값을 넘긴다.
     */
    public static Member createUser(String name, String email, String encodedPassword) {
        return new Member(name, email, encodedPassword, MemberRole.USER);
    }
    // 연관관계 편의 메서드

    // 비즈니스 로직 - Domain Driven Development를 할거라면? 선택사항
}

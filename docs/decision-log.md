# Decision Log

기술 선택 중에서 **코드만 봐서는 알 수 없는 것**을 기록한다. 무엇을 골랐는지가 아니라 무엇을 버렸고 왜 버렸는지를 남긴다.

## 2026-08-28. Database를 MySQL에서 PostgreSQL로 변경

폐기한 안 두 개다.

1. MySQL + Docker Compose (로드맵 원안)
2. 개발 중에는 H2, 막바지에만 PostgreSQL

선택은 로컬, CI, 운영 모두 PostgreSQL이다. 로컬은 Docker Compose로 `postgres:18-alpine`을 띄우고, 배포는 Supabase를 쓴다.

이유:

- 최종 배포 대상을 Supabase로 정했고 Supabase는 PostgreSQL이다. 로컬을 MySQL로 두면 방언 차이를 배포 직전에 몰아서 겪는다.
- H2안을 버린 이유가 더 크다. 이 프로젝트의 핵심 목표는 좌석 예매 동시성이다(Milestone 11). 그런데 H2는 `SELECT ... FOR UPDATE`의 락 동작, 트랜잭션 격리 수준, 데드락 감지가 PostgreSQL과 다르다. H2에서 통과한 동시성 테스트는 PostgreSQL에서의 근거가 되지 못한다.
- Docker Compose는 Redis와 Kafka에서 어차피 필요하다. H2로 미뤄서 얻는 이득이 "초반에 Docker를 안 쓴다" 하나뿐이었다.

영향:

- Testcontainers 대상이 PostgreSQL로 바뀐다 (Issue #32).
- `spring-boot-starter-data-jpa`가 들어간 시점부터 DataSource 없이는 애플리케이션이 부팅되지 않는다. 따라서 Backend CI에도 PostgreSQL service container가 필요하다.

## 2026-08-28. Backend를 Java 21로 재생성

Java 17에서 21로 올리는 것 자체는 `build.gradle` 한 줄이지만, JPA와 Validation, Lombok 의존성을 동시에 추가해야 해서 start.spring.io에서 새로 받아 교체했다. Spring Boot는 4.1.0에서 4.1.1로 같이 올라갔다.

주의할 점이 하나 있다. Spring Boot 4부터 web starter 이름이 `spring-boot-starter-web`이 아니라 `spring-boot-starter-webmvc`다. 테스트 쪽 import 경로도 `org.springframework.boot.webmvc.test.autoconfigure`로 바뀌었다. 인터넷의 Boot 3 예제를 그대로 붙이면 컴파일되지 않는다.

## 2026-08-28. Frontend에서 TypeScript 제거

Issue #6의 원안은 Vite + React + TypeScript였으나 순수 JavaScript로 바꿨다.

이유는 학습 범위를 React 자체에 집중하기 위해서다. 백엔드 쪽에 새로 익힐 것이 많은 상태에서 타입 시스템까지 동시에 다루지 않기로 했다.

영향:

- `npm run build`가 `tsc -b` 없이 `vite build` 단독으로 동작한다.
- ESLint에서 TypeScript 파서를 걷어냈다. JSX 파싱을 그 파서가 대신 해주고 있었으므로, `parserOptions.ecmaFeatures.jsx`를 직접 켜야 한다.

## 2026-09-02. Spring Security를 두 단계로 나눠 도입

Issue #14(회원가입)에서 비밀번호 해시가 필요했다. `BCryptPasswordEncoder`는 `spring-boot-starter-security`에 들어 있는데, 이 스타터를 넣는 순간 자동 설정된 필터 체인이 모든 요청에 인증을 걸어 기존 `/api/health` 테스트가 깨진다.

폐기한 안은 #14에서 스타터와 `SecurityFilterChain`을 한꺼번에 작성하는 것이다. 회원가입 이슈에 인증 설계가 딸려 들어와 범위가 커진다.

선택은 #14에서 `spring-security-crypto`만 넣는 것이다. 이 모듈에는 `spring-security-config`와 `spring-security-web`이 없어서 필터 체인 자동 설정이 아예 켜지지 않는다. `PasswordEncoder` 빈만 쓰고 넘어갔고, 필터 체인은 인증이 실제로 필요해진 #15에서 `spring-boot-starter-security`와 함께 작성했다.

`PasswordEncoderConfig`의 빈은 #15에서 그대로 재사용된다. 교체 비용은 없었다.

## 2026-09-02. JWT 라이브러리로 jjwt 선택

폐기한 안은 `spring-boot-starter-oauth2-resource-server`다. `JwtDecoder`/`JwtEncoder` 빈과 `oauth2ResourceServer()` DSL을 쓰면 커스텀 필터와 엔트리포인트를 거의 작성하지 않아도 된다.

선택은 jjwt 0.12.6이다. 코드는 더 많지만 토큰 생성, 서명, 파싱과 `OncePerRequestFilter`를 직접 쓰는 편이 이 저장소의 학습 목적에 맞다.

주의할 점이 하나 있다. `jjwt-jackson`은 Jackson 2를 끌어온다. 본체는 Boot 4라서 Jackson 3(`tools.jackson`)을 쓰므로 두 버전이 함께 있게 된다. jjwt 내부 직렬화에만 쓰여서 충돌하지 않는다.

## 2026-08-28. Issue #8보다 Issue #9를 먼저 진행

스택 변경으로 Backend를 재생성하면서 JPA가 들어왔고, DB 없이는 `./gradlew test`가 실패하는 상태가 됐다. 이 상태를 오래 두지 않으려고 Issue #9를 앞당겼다.

Issue #8(Frontend CI)은 DB와 무관해서 순서를 바꿔도 충돌하지 않는다.

# 02. Core Domain

## Backend 디렉토리 구조 원칙

Milestone 5부터 실제 핵심 도메인을 구현할 때는 기능 단위 패키지 안에 계층을 나누어 생성한다.

기본 구조:

```text
feature/
├── controller/
├── service/
├── domain/
└── repository/
```

예시:

```text
member/
├── controller/
├── service/
├── domain/
└── repository/

event/
├── controller/
├── service/
├── domain/
└── repository/

reservation/
├── controller/
├── service/
├── domain/
└── repository/
```

- `controller`: HTTP 요청과 응답을 담당한다.
- `service`: 비즈니스 흐름을 담당한다.
- `domain`: Entity, 값 객체, 도메인 규칙을 둔다.
- `repository`: 저장소 접근 계약과 구현을 둔다.
- `global`, `common`, `config`, `exception` 같은 공통 구조는 도메인 계층으로 억지로 나누지 않는다.
- `HealthController`처럼 단순 상태 확인용 API는 `global/common`에 가볍게 둘 수 있다.

## Milestone 5. 회원과 인증

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #12 | 공통 응답과 예외 구조 작성 | 성공/실패 응답 형식이 통일된다. | `feat: add common api response` |
| #13 | Member Entity 작성 | Member 엔티티가 JPA Entity로 동작한다. | `feat: add member entity` |
| #14 | Signup API 구현 | `POST /api/members/signup` 요청이 동작한다. | `feat: implement member signup` |
| #15 | Login API와 JWT 인증 구현 | `POST /api/auth/login` 요청으로 JWT를 발급받을 수 있다. | `feat: add jwt authentication` |

## Milestone 6. 공연과 좌석

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #16 | Event Entity와 등록 API 구현 | `POST /api/events` 요청으로 공연 등록이 가능하다. | `feat: implement event creation api` |
| #17 | Event 목록/상세 조회 API 구현 | `GET /api/events`, `GET /api/events/{eventId}` 요청이 동작한다. | `feat: implement event query api` |
| #18 | Seat Entity와 좌석 생성 API 구현 | 특정 공연에 좌석을 생성할 수 있다. | `feat: implement seat creation api` |
| #19 | Seat 조회 API 구현 | `GET /api/events/{eventId}/seats` 요청이 동작한다. | `feat: implement seat query api` |

## Milestone 7. Frontend 화면 연동

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #20 | Backend Health 연동 화면 구현 | Frontend 화면에서 Backend health 응답을 확인할 수 있다. | `feat: connect frontend with backend api` |
| #21 | 회원가입/로그인 화면 구현 | React 화면에서 회원가입과 로그인이 가능하다. | `feat: add auth pages` |
| #22 | 공연 목록/상세 화면 구현 | React 화면에서 공연 목록과 상세 정보를 확인할 수 있다. | `feat: add event pages` |
| #23 | 좌석 선택 화면 구현 | React 화면에서 좌석을 선택할 수 있다. | `feat: add seat selection page` |

## Milestone 8. 예매와 결제

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #24 | Reservation Entity 작성 | 예매 정보를 저장할 수 있다. | `feat: add reservation entity` |
| #25 | 기본 예매 API 구현 | `POST /api/reservations` 요청으로 좌석 예매가 가능하다. | `feat: implement reservation api` |
| #26 | 내 예매 목록과 예매 취소 API 구현 | `GET /api/reservations/me`, `DELETE /api/reservations/{reservationId}` 요청이 동작한다. | `feat: implement reservation management api` |
| #27 | Payment Entity와 결제 상태 API 구현 | 결제 상태 변경 API가 동작한다. | `feat: implement payment api` |
| #28 | React 예매 흐름 구현 | React 화면에서 예매, 조회, 취소가 가능하다. | `feat: add reservation flow` |

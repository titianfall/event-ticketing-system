# Project Structure

이 문서는 Ticketing Platform의 최종 목표 디렉토리 구조를 기록한다.

중요한 원칙은 다음과 같다.

- 이 구조는 최종 목표이며, 처음부터 모든 디렉토리를 만들지 않는다.
- 실제 디렉토리와 파일은 해당 이슈를 진행할 때 생성한다.
- 빈 디렉토리를 Git에 올리기 위해 의미 없는 `.gitkeep` 파일을 만들지 않는다.
- 구조가 바뀌면 이 문서를 먼저 갱신하고, 실제 변경은 관련 이슈에서 수행한다.
- 핵심 도메인은 기능 단위 패키지를 만들고, 그 안에서 `controller`, `service`, `domain`, `repository` 계층으로 나눈다.
- `global`, `common`, `config`, `exception` 같은 공통 구조는 도메인 계층으로 억지로 분리하지 않는다.

## 생성 시점

| 경로 | 생성 시점 |
| --- | --- |
| `backend/` | Issue #3. Spring Boot Backend 생성 |
| `frontend/` | Issue #6. React Frontend 생성 |
| `.github/workflows/` | Issue #5. Backend CI 첫 실습 |
| `docker-compose.yml` | Issue #9. MySQL Docker Compose 구성 |
| `.env.example` | Issue #9. MySQL Docker Compose 구성 |
| `scripts/` | Issue #9. MySQL Docker Compose 구성 |
| `backend/Dockerfile` | Issue #35. Dockerfile 작성 |
| `frontend/Dockerfile` | Issue #35. Dockerfile 작성 |
| `renovate.json` | Issue #57. Renovate 설정 |
| `.github/dependabot.yml` | Issue #56. Dependabot 설정 |
| `docs/architecture.md` | Issue #58. architecture.md 작성 |
| `docs/erd.md` | Issue #59. erd.md 작성 |
| `docs/api.md` | Issue #60. api.md 작성 |
| `docs/testing.md` | Issue #61. testing.md 작성 |
| `docs/ci-cd.md` | Issue #62. ci-cd.md 작성 |
| `docs/security.md` | Issue #63. security.md 작성 |

## 최종 목표 구조

```text
ticketing-platform/
├── README.md
├── docker-compose.yml
├── .env.example
├── .gitignore
├── renovate.json
│
├── .github/
│   ├── dependabot.yml
│   └── workflows/
│       ├── backend-ci.yml
│       ├── frontend-ci.yml
│       ├── docker.yml
│       ├── security.yml
│       ├── codeql.yml
│       └── deploy.yml
│
├── backend/
│   ├── build.gradle
│   ├── settings.gradle
│   ├── gradlew
│   ├── gradlew.bat
│   ├── Dockerfile
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/ticketing/
│   │   │   │   ├── TicketingApplication.java
│   │   │   │   ├── member/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── domain/
│   │   │   │   │   └── repository/
│   │   │   │   ├── auth/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── domain/
│   │   │   │   │   └── repository/
│   │   │   │   ├── event/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── domain/
│   │   │   │   │   └── repository/
│   │   │   │   ├── seat/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── domain/
│   │   │   │   │   └── repository/
│   │   │   │   ├── reservation/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── domain/
│   │   │   │   │   └── repository/
│   │   │   │   ├── payment/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── domain/
│   │   │   │   │   └── repository/
│   │   │   │   ├── notification/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── domain/
│   │   │   │   │   └── repository/
│   │   │   │   ├── global/
│   │   │   │   │   ├── config/
│   │   │   │   │   ├── exception/
│   │   │   │   │   ├── response/
│   │   │   │   │   ├── security/
│   │   │   │   │   └── common/
│   │   │   │   └── infra/
│   │   │   │       ├── redis/
│   │   │   │       ├── kafka/
│   │   │   │       └── external/
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── application-local.yml
│   │   │       ├── application-test.yml
│   │   │       └── application-prod.yml
│   │   └── test/
│   │       └── java/com/example/ticketing/
│   │           ├── unit/
│   │           ├── integration/
│   │           ├── concurrency/
│   │           └── support/
│   └── build/
│       └── reports/
│           └── jacoco/
│
├── frontend/
│   ├── package.json
│   ├── package-lock.json
│   ├── Dockerfile
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── index.html
│   └── src/
│       ├── main.tsx
│       ├── App.tsx
│       ├── api/
│       ├── components/
│       ├── pages/
│       │   ├── auth/
│       │   ├── events/
│       │   ├── seats/
│       │   └── reservations/
│       ├── routes/
│       ├── hooks/
│       ├── stores/
│       ├── types/
│       └── styles/
│
├── docs/
│   ├── roadmap.md
│   ├── project-structure.md
│   ├── architecture.md
│   ├── erd.md
│   ├── api.md
│   ├── concurrency.md
│   ├── testing.md
│   ├── ci-cd.md
│   ├── security.md
│   ├── deployment.md
│   ├── coding-convention.md
│   ├── decision-log.md
│   └── roadmap/
│       ├── 00-overview.md
│       ├── 01-project-backend-frontend.md
│       ├── 02-core-domain.md
│       ├── 03-test-ci-concurrency.md
│       ├── 04-redis-kafka-security.md
│       └── 05-docs-deployment.md
│
└── scripts/
    ├── init-db.sql
    └── run-local.sh
```

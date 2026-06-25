# 05. Docs, Deployment

## Milestone 15. 문서화

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #58 | architecture.md 작성 | React, Spring Boot, MySQL, Redis, Kafka, Docker Compose, CI/CD 구조를 설명할 수 있다. | `docs: add architecture document` |
| #59 | erd.md 작성 | Member, Event, Seat, Reservation, Payment, Notification 관계와 설계 이유가 문서화된다. | `docs: add erd document` |
| #60 | api.md 작성 | Auth, Member, Event, Seat, Reservation, Payment API가 정리된다. | `docs: add api document` |
| #61 | testing.md 작성 | JUnit, Mockito, SpringBootTest, Testcontainers, Jacoco 전략이 정리된다. | `docs: add testing document` |
| #62 | ci-cd.md 작성 | Backend CI, Frontend CI, Docker CI, Security CI, Deploy Flow가 정리된다. | `docs: add ci cd document` |
| #63 | security.md 작성 | SQL Injection, XSS, JWT, npm audit, Trivy, Dependency-Check, CodeQL 전략이 정리된다. | `docs: add security document` |

## Milestone 16. 배포와 최종 점검

| Issue | 제목 | 직접 작성할 파일 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- | --- |
| #64 | 배포 준비 | `application-prod.yml`, 배포 설정 초안 | 로컬에서 production profile 실행이 가능하다. | `chore: prepare production deployment` |
| #65 | CD Workflow 실습 | `.github/workflows/deploy.yml` | 기본 브랜치 merge 시 배포 플로가 실행될 준비가 된다. | `ci: add deployment workflow` |
| #66 | 최종 README 정리 | `README.md` | README만 읽어도 프로젝트의 강점을 이해할 수 있다. | `docs: finalize project readme` |
| #67 | 최종 점검 | 전체 프로젝트 | 포트폴리오 제출 가능한 상태가 된다. | `chore: final project cleanup` |

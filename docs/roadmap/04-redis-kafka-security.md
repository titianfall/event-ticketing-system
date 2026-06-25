# 04. Redis, Kafka, Security

## Milestone 12. Redis 확장

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #41 | Redis Docker Compose 추가 | Backend에서 Redis 연결이 성공한다. | `chore: add redis to docker compose` |
| #42 | Redis Cache 적용 | 동일 공연 조회 시 캐시가 동작한다. | `feat: add redis cache for event query` |
| #43 | Redis Lock 실험 | Redis Lock 방식의 장단점을 설명할 수 있다. | `feat: add redis lock reservation strategy` |
| #44 | Redis Testcontainers 적용 | Redis 테스트가 로컬 Redis 없이 실행된다. | `test: add redis testcontainers tests` |

## Milestone 13. Kafka와 비동기 알림

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #45 | Kafka Docker Compose 추가 | Kafka UI에서 Topic을 확인할 수 있다. | `chore: add kafka to docker compose` |
| #46 | 예약 이벤트 발행 | 예매 완료 시 Kafka 메시지가 발행된다. | `feat: publish reservation completed event` |
| #47 | 예약 이벤트 소비와 알림 처리 | Kafka Consumer가 예약 이벤트를 정상 소비한다. | `feat: consume reservation event for notification` |
| #48 | 비동기 처리 적용 | 알림 처리가 요청 흐름을 막지 않는다. | `feat: add async notification processing` |
| #49 | Kafka Testcontainers 적용 | Kafka 테스트가 로컬 Kafka 없이 실행된다. | `test: add kafka testcontainers tests` |

## Milestone 14. 보안과 품질 자동화

보안/품질 자동화 이슈도 사용자가 먼저 설정 파일을 작성하고 Codex가 리뷰한다.

| Issue | 제목 | 직접 작성할 파일 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- | --- |
| #50 | Swagger/OpenAPI 적용 | Backend 설정과 API 문서 애노테이션 | `/swagger-ui/index.html` 접속이 가능하다. | `docs: add swagger api documentation` |
| #51 | npm audit 실습 | `frontend/package.json`, `.github/workflows/frontend-ci.yml` | Critical 취약점이 있으면 CI가 실패한다. | `security: add npm audit check` |
| #52 | Trivy 실습 | `.github/workflows/security.yml` | Docker 이미지 또는 파일시스템 취약점을 검사한다. | `security: add trivy image scan` |
| #53 | OWASP Dependency-Check 실습 | `backend/build.gradle`, `.github/workflows/security.yml` | 의존성 취약점 리포트가 생성된다. | `security: add owasp dependency check` |
| #54 | CodeQL 실습 | `.github/workflows/codeql.yml` | GitHub Security 탭에서 분석 결과를 확인할 수 있다. | `security: add codeql analysis` |
| #55 | SonarCloud 또는 SonarQube 적용 | Sonar 설정과 CI 연동 | Pull Request에서 품질 분석 결과를 확인할 수 있다. | `ci: add sonar quality analysis` |
| #56 | Dependabot 설정 | `.github/dependabot.yml` | Dependabot PR이 생성된다. | `chore: add dependabot configuration` |
| #57 | Renovate 설정 | `renovate.json` | Renovate PR이 생성된다. | `chore: add renovate configuration` |

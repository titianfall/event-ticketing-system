# 03. Test, CI, Concurrency

## Milestone 9. 테스트 심화

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #29 | Service 단위 테스트와 Mockito 적용 | DB 없이 Service 단위 테스트가 실행된다. | `test: add service unit tests` |
| #30 | Controller 테스트 작성 | 주요 Controller 테스트가 통과한다. | `test: add controller tests` |
| #31 | Jacoco 적용 | Jacoco Report가 생성된다. | `test: add jacoco coverage report` |
| #32 | Testcontainers MySQL 적용 | 로컬 MySQL 없이 통합 테스트가 실행된다. | `test: add mysql testcontainers integration tests` |

## Milestone 10. CI 실습 확장

CI 실습 이슈는 사용자가 먼저 YAML 또는 설정을 작성하고, Codex가 리뷰하는 방식으로 진행한다.

| Issue | 제목 | 직접 작성할 파일 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- | --- |
| #33 | Backend CI 개선 실습 | `.github/workflows/backend-ci.yml` | Backend CI에서 test와 build가 실행된다. | `ci: improve backend ci workflow` |
| #34 | Frontend CI 개선 실습 | `.github/workflows/frontend-ci.yml` | Frontend CI에서 lint, test, build, audit가 실행된다. | `ci: improve frontend ci workflow` |
| #35 | Dockerfile 작성 | `backend/Dockerfile`, `frontend/Dockerfile` | Backend와 Frontend Docker image build가 성공한다. | `chore: add dockerfiles` |
| #36 | Docker CI 개선 실습 | `.github/workflows/docker.yml` | CI에서 Dockerfile build와 compose config 검증이 실행된다. | `ci: add docker build workflow` |

## Milestone 11. 동시성

| Issue | 제목 | 완료 조건 | 커밋 예시 |
| --- | --- | --- | --- |
| #37 | 동시 예매 문제 재현 테스트 작성 | 중복 예매 문제가 테스트로 재현된다. | `test: reproduce concurrent reservation issue` |
| #38 | Optimistic Lock 적용 | 동시에 같은 좌석 예매 시 하나만 성공한다. | `feat: prevent duplicate reservation with optimistic lock` |
| #39 | Pessimistic Lock 비교 구현 | Optimistic Lock과 Pessimistic Lock 차이를 설명할 수 있다. | `feat: add pessimistic lock reservation strategy` |
| #40 | concurrency.md 작성 | 면접에서 설명 가능한 수준의 문서가 완성된다. | `docs: document reservation concurrency strategy` |

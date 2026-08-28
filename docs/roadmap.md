# Project Roadmap

전체 로드맵은 구간별 문서로 나누어 관리한다.

## 진행 원칙

- 모든 작업은 마일스톤과 이슈 단위로 진행한다.
- 한 번에 하나의 이슈만 진행한다.
- 각 이슈를 시작할 때 목적, 필요성, 학습 개념, 수정 파일, 구현 순서, 주의할 점, 완료 조건을 먼저 설명한다.
- 사용자가 직접 작성할 수 있도록 TODO, 힌트, 부분 코드, 확인 명령을 먼저 제공한다.
- 사용자가 "직접 만들어줘", "코드까지 작성해줘", "막혔어"라고 요청하면 그때 완성 코드를 작성한다.
- Issue #1은 로컬 커밋 준비까지만 진행하고, Issue #2부터 이슈 단위 브랜치와 PR 흐름을 사용한다.
- CI/CD 이슈는 사용자가 먼저 직접 작성하고 Codex는 리뷰, 디버깅, 개선 힌트, 정답 예시 순서로 돕는다.

## 구간별 로드맵

- [00. Overview](roadmap/00-overview.md)
- [01. Project, Backend, Frontend](roadmap/01-project-backend-frontend.md)
- [02. Core Domain](roadmap/02-core-domain.md)
- [03. Test, CI, Concurrency](roadmap/03-test-ci-concurrency.md)
- [04. Redis, Kafka, Security](roadmap/04-redis-kafka-security.md)
- [05. Docs, Deployment](roadmap/05-docs-deployment.md)

## 현재 위치

- 완료: Milestone 1. 프로젝트 초기화
- 완료: Milestone 2. Backend 기초
- 완료: Milestone 3. Frontend 기초
- 진행 중: Milestone 4. 로컬 개발 환경과 JPA — Issue #9, #10 완료, Issue #11 남음
- 다음 이슈: Issue #11. Docker Compose 검증 CI 실습

스택 변경(PostgreSQL, Java 21, JavaScript)을 반영하면서 Backend 재생성과 Frontend 전환을 먼저 처리했고,
그 과정에서 Issue #8보다 Issue #9, #10을 앞서 진행했다. 배경은 [기술 선택 기록](decision-log.md)에 있다.

## 추천 커밋 메시지

```bash
ci: add initial frontend ci workflow
```

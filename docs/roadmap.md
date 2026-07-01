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
- 진행 중: Milestone 3. Frontend 기초
- 완료 이슈: Issue #6. React Frontend 생성
- 다음 이슈: Issue #7. Frontend npm scripts와 테스트 기초

## 추천 커밋 메시지

```bash
test: add frontend test script
```

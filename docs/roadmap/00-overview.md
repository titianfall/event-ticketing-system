# 00. Overview

## 전체 일정 요약

| Milestone | 주제 | Issue |
| --- | --- | --- |
| 1 | 프로젝트 초기화 | #1 - #2 |
| 2 | Backend 기초 | #3 - #5 |
| 3 | Frontend 기초 | #6 - #8 |
| 4 | 로컬 개발 환경과 JPA | #9 - #11 |
| 5 | 회원과 인증 | #12 - #15 |
| 6 | 공연과 좌석 | #16 - #19 |
| 7 | Frontend 화면 연동 | #20 - #23 |
| 8 | 예매와 결제 | #24 - #28 |
| 9 | 테스트 심화 | #29 - #32 |
| 10 | CI 실습 확장 | #33 - #36 |
| 11 | 동시성 | #37 - #40 |
| 12 | Redis 확장 | #41 - #44 |
| 13 | Kafka와 비동기 알림 | #45 - #49 |
| 14 | 보안과 품질 자동화 | #50 - #57 |
| 15 | 문서화 | #58 - #63 |
| 16 | 배포와 최종 점검 | #64 - #67 |

## 작업 진행 규칙

1. 모든 작업은 마일스톤과 이슈 단위로만 진행한다.
2. 한 번에 여러 마일스톤을 진행하지 않는다.
3. 같은 마일스톤 안에서도 한 번에 하나의 이슈만 진행한다.
4. 이전 이슈의 완료 조건을 만족하지 못하면 다음 이슈로 넘어가지 않는다.
5. 각 이슈를 시작할 때 먼저 목적, 필요성, 학습 개념, 수정 파일, 구현 순서, 주의할 점, 완료 조건을 설명한다.
6. 설명 후 사용자가 직접 작성할 수 있도록 TODO, 힌트, 부분 코드, 확인 명령을 먼저 제공한다.
7. 사용자가 "직접 만들어줘", "코드까지 작성해줘", "막혔어"라고 요청하면 그때 완성 코드를 작성한다.
8. 각 이슈 완료 후 실행 방법, 문서 업데이트 여부, README 체크리스트 갱신 여부, 커밋 메시지를 제안한다.
9. 커밋 메시지는 Conventional Commits 형식을 사용한다.

## 디렉토리 생성 원칙

- 최종 디렉토리 구조는 [project-structure.md](../project-structure.md)에 목표로 기록한다.
- 실제 디렉토리와 파일은 해당 이슈를 진행할 때 만든다.
- 아직 사용하지 않는 빈 디렉토리를 미리 만들기 위해 `.gitkeep`을 남발하지 않는다.
- 예를 들어 `backend/`는 Backend 생성 이슈에서, `frontend/`는 Frontend 생성 이슈에서, `.github/workflows/`는 첫 CI 이슈에서 만든다.

## Branch, PR, CI/CD 운영 원칙

### 브랜치 분기 시점

- Issue #1은 프로젝트의 첫 커밋이므로 기본 브랜치에서 로컬 커밋 준비까지만 진행한다.
- Issue #1에서는 원격 push와 PR 생성을 진행하지 않는다.
- Issue #2부터는 이슈마다 새 브랜치를 만든다.
- 브랜치 이름은 `issue/{issue-number}-{short-name}` 형식을 사용한다.
- 예: `issue/2-document-project-structure`, `issue/3-init-backend`, `issue/5-backend-ci`.

### Pull Request 시작 시점

- Issue #2부터 PR 흐름을 사용한다.
- CI가 아직 없더라도 PR을 만들어 변경 범위, 커밋 메시지, 문서 변경을 리뷰하는 습관을 들인다.
- PR 하나는 이슈 하나만 포함한다.
- PR 본문에는 목적, 변경 파일, 확인 방법, 다음 이슈를 적는다.

### CI 시작 시점

- Backend CI는 Issue #5부터 시작한다. 이때 `./gradlew test`를 자동 실행한다.
- Frontend CI는 Issue #8부터 시작한다. 이때 `npm ci`, `npm run lint`, `npm run test`, `npm run build`를 자동 실행한다.
- Docker CI는 Issue #11에서 `docker compose config` 검증으로 시작하고, Issue #36에서 Docker image build까지 확장한다.
- 보안 CI는 Issue #51부터 `npm audit`, Trivy, Dependency-Check, CodeQL 순서로 확장한다.

### CD 시작 시점

- CD는 배포 대상과 production profile이 준비된 뒤인 Issue #65에서 시작한다.
- CD workflow는 기본 브랜치 병합 이후에만 실행되도록 구성한다.
- 배포 자동화 전까지는 CI만 사용하고 CD는 만들지 않는다.

## CI/CD 학습 운영 규칙

- CI/CD는 프로젝트 마지막에 한 번에 만들지 않고 중간중간 작은 실습 이슈로 나누어 진행한다.
- `package-lock.json`은 직접 작성하지 않는다. `npm install` 또는 `npm ci`가 자동으로 관리한다.
- Frontend 명령은 `frontend/package.json`의 `scripts`에 직접 작성한다.
- Backend JUnit 테스트는 `backend/build.gradle` 설정과 `./gradlew test`로 실행한다.
- GitHub Actions 워크플로는 `.github/workflows/*.yml`에 직접 작성한다.
- CI/CD 이슈에서는 Codex가 먼저 완성 파일을 만들지 않고, 사용자가 작성한 YAML, Gradle, package scripts를 리뷰한다.

## 자주 사용할 명령

```bash
cd frontend
npm ci
npm run lint
npm run test
npm run build
npm audit
```

```bash
cd backend
./gradlew test
./gradlew build
```

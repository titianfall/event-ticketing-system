# 01. Project, Backend, Frontend

## Milestone 1. 프로젝트 초기화

### Issue #1. Git Repository 생성

목표: 프로젝트 저장소의 기본 문서와 Git 관리 기준을 만든다.

작업:

- Git 저장소 상태 확인
- remote 연결 상태 확인
- README.md 작성
- .gitignore 작성
- docs/roadmap.md 작성
- docs/roadmap 디렉토리에 구간별 로드맵 작성

완료 조건:

- README.md에 프로젝트 소개와 초기 일정이 있다.
- .gitignore가 있다.
- docs/roadmap.md와 docs/roadmap/* 문서가 있다.
- 첫 커밋을 만들 준비가 되어 있다.
- 원격 push와 PR 생성은 진행하지 않는다.

커밋 예시:

```bash
chore: initialize project repository
```

### Issue #2. 최종 디렉토리 구조 목표 문서화

목표: 최종 디렉토리 구조를 문서로 정리하고, 실제 디렉토리는 필요한 이슈에서 생성한다는 원칙을 확정한다.

작업:

- docs/project-structure.md 작성
- 최종 목표 디렉토리 구조 기록
- 각 주요 디렉토리가 생성될 이슈 연결
- 빈 디렉토리를 미리 만들지 않는 원칙 기록

완료 조건:

- docs/project-structure.md에 최종 목표 구조가 정리되어 있다.
- 실제 디렉토리는 해당 이슈에서 생성한다는 원칙이 문서화되어 있다.
- 아직 Backend/Frontend 디렉토리는 생성하지 않는다.

커밋 예시:

```bash
docs: document target project structure
```

## Milestone 2. Backend 기초

### Issue #3. Spring Boot Backend 생성

목표: Spring Boot 기본 프로젝트를 만든다.

완료 조건:

- Backend 애플리케이션이 실행된다.
- `GET /api/health` 요청에 정상 응답한다.

커밋 예시:

```bash
feat: initialize spring boot backend
```

### Issue #4. Backend JUnit 기초 테스트 작성

목표: Backend에서 JUnit 테스트를 실행하는 흐름을 익힌다.

완료 조건:

- 테스트가 1개 이상 통과한다.
- `./gradlew test` 실행 방법이 기록된다.

커밋 예시:

```bash
test: add backend smoke test
```

### Issue #5. Backend CI 첫 실습

목표: GitHub Actions로 Backend 테스트를 자동 실행하는 워크플로를 직접 작성해본다.

진행 방식:

- 사용자가 `.github/workflows/backend-ci.yml`을 먼저 작성한다.
- Codex는 YAML 구조, Gradle cache, test/build 순서를 리뷰한다.

완료 조건:

- Backend CI가 `./gradlew test`를 실행한다.

커밋 예시:

```bash
ci: add initial backend ci workflow
```

## Milestone 3. Frontend 기초

### Issue #6. React Frontend 생성

목표: Vite + React + TypeScript 프로젝트를 만든다.

완료 조건:

- `npm run dev` 실행 시 화면이 열린다.

커밋 예시:

```bash
feat: initialize react frontend
```

### Issue #7. Frontend npm scripts와 테스트 기초

목표: Frontend에서 lint, test, build 스크립트를 직접 구성한다.

주의:

- `package-lock.json`은 직접 작성하지 않는다.
- 의존성 설치 후 npm이 자동으로 갱신한 결과만 확인한다.

완료 조건:

- `npm run lint`, `npm run test`, `npm run build` 실행 흐름이 준비된다.

커밋 예시:

```bash
test: add frontend test script
```

### Issue #8. Frontend CI 첫 실습

목표: GitHub Actions로 Frontend 검사를 자동 실행하는 워크플로를 직접 작성해본다.

진행 방식:

- 사용자가 `.github/workflows/frontend-ci.yml`을 먼저 작성한다.
- Codex는 Node 버전, `npm ci`, lint/test/build 순서를 리뷰한다.

완료 조건:

- Frontend CI가 `npm ci`, `npm run lint`, `npm run test`, `npm run build`를 실행한다.

커밋 예시:

```bash
ci: add initial frontend ci workflow
```

## Milestone 4. 로컬 개발 환경과 JPA

### Issue #9. MySQL Docker Compose 구성

목표: MySQL을 Docker Compose로 실행한다.

완료 조건:

- `docker compose up -d`로 MySQL이 실행된다.

커밋 예시:

```bash
chore: add mysql docker compose setup
```

### Issue #10. Spring Data JPA 연결

목표: Backend와 MySQL을 JPA로 연결한다.

완료 조건:

- Backend 실행 시 MySQL 연결이 성공한다.

커밋 예시:

```bash
feat: configure spring data jpa
```

### Issue #11. Docker Compose 검증 CI 실습

목표: CI에서 Docker 설정을 검증하는 흐름을 직접 작성해본다.

진행 방식:

- 사용자가 `.github/workflows/docker.yml` 초안을 작성한다.
- Codex는 `docker compose config` 검증 흐름을 리뷰한다.

완료 조건:

- CI에서 Docker Compose 문법 검증이 가능하다.

커밋 예시:

```bash
ci: add docker compose validation workflow
```

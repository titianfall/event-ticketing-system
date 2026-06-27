# Issue #5. Backend CI 첫 실습

이 문서는 Issue #5를 진행하면서 GitHub Actions로 Backend 테스트를 자동 실행하는 흐름을 이해하기 위한 학습 노트다.

## 목적

GitHub Actions workflow를 만들어서 Pull Request 또는 push 시 Backend 테스트가 자동으로 실행되게 한다.

지금까지는 로컬에서 직접 다음 명령을 실행해서 테스트를 확인했다.

```powershell
cd backend
.\gradlew.bat test
```

이번 이슈에서는 GitHub가 같은 테스트를 자동으로 실행하도록 만든다.

## 완료 조건

- `.github/workflows/backend-ci.yml` 파일이 있다.
- GitHub Actions에서 Backend CI가 실행된다.
- CI 안에서 `./gradlew test`가 성공한다.

## 작업 범위

이번 이슈에서 하는 일:

- GitHub Actions workflow 파일 생성
- Pull Request와 push 이벤트 설정
- Ubuntu runner에서 Java 17 설정
- Gradle cache 설정
- `backend` 디렉토리에서 `./gradlew test` 실행

이번 이슈에서 하지 않는 일:

- Frontend CI 설정
- Docker Compose CI 설정
- Security scan 설정
- Jacoco coverage 설정
- 배포 자동화
- `./gradlew build`까지 강제 실행

## 새로 만들 파일

파일 위치:

```text
.github/workflows/backend-ci.yml
```

## 권장 workflow 내용

```yaml
name: Backend CI

on:
  pull_request:
    branches:
      - main
  push:
    branches:
      - main
      - "issue/**"

jobs:
  backend-test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout source code
        uses: actions/checkout@v4

      - name: Set up Java 17
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
          cache: gradle

      - name: Grant execute permission for Gradle wrapper
        run: chmod +x gradlew
        working-directory: backend

      - name: Run backend tests
        run: ./gradlew test
        working-directory: backend
```

## workflow 구성 설명

### name

```yaml
name: Backend CI
```

GitHub Actions 화면에 표시될 workflow 이름이다.

### on

```yaml
on:
  pull_request:
    branches:
      - main
  push:
    branches:
      - main
      - "issue/**"
```

다음 상황에서 CI를 실행한다.

- `main` 브랜치로 Pull Request를 만들 때
- `main` 브랜치에 push할 때
- `issue/`로 시작하는 브랜치에 push할 때

### runs-on

```yaml
runs-on: ubuntu-latest
```

GitHub가 제공하는 Ubuntu 환경에서 workflow를 실행한다.

### checkout

```yaml
uses: actions/checkout@v4
```

CI 서버에 현재 저장소 코드를 내려받는다.

### setup-java

```yaml
uses: actions/setup-java@v4
with:
  distribution: temurin
  java-version: 17
  cache: gradle
```

Java 17을 설치하고 Gradle cache를 활성화한다.

이 프로젝트는 Backend가 Java 17 기준이므로 CI에서도 Java 17을 사용한다.

### chmod

```yaml
run: chmod +x gradlew
working-directory: backend
```

GitHub Actions는 Linux 환경에서 실행되므로 `gradlew` 파일에 실행 권한이 필요할 수 있다.

Windows에서는 `gradlew.bat`을 사용하지만, CI에서는 Linux runner 기준으로 `gradlew`를 사용한다.

### test

```yaml
run: ./gradlew test
working-directory: backend
```

`backend` 디렉토리에서 Gradle 테스트를 실행한다.

이번 이슈의 핵심 완료 조건이다.

## 직접 진행 순서

Issue #4가 `main`에 반영된 뒤 시작한다.

```powershell
git switch main
git pull
git switch -c issue/5-backend-ci
```

workflow 디렉토리를 만든다.

```powershell
mkdir .github\workflows
```

다음 파일을 작성한다.

```text
.github/workflows/backend-ci.yml
```

로컬에서 Backend 테스트가 통과하는지 먼저 확인한다.

```powershell
cd backend
.\gradlew.bat test
```

## Git 확인

변경 파일을 확인한다.

```powershell
git status --short
```

주의할 점:

```text
.github/modernize/
```

같은 다른 untracked 파일이 보이면 같이 커밋하지 않는다.

이번 이슈에서는 workflow 파일 하나만 추가한다.

```powershell
git add .github/workflows/backend-ci.yml
git status --short
```

정상 예시:

```text
A  .github/workflows/backend-ci.yml
```

## 커밋

```powershell
git commit -m "ci: add initial backend ci workflow"
```

## Push와 PR 확인

```powershell
git push -u origin issue/5-backend-ci
```

GitHub에서 Pull Request를 만든 뒤 다음을 확인한다.

- PR 화면의 Checks 탭
- GitHub Actions 탭
- `Backend CI` workflow 성공 여부

## 추천 커밋 메시지

```bash
ci: add initial backend ci workflow
```

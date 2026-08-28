# 실행 방법

이 문서는 티켓 예매 시스템의 로컬 실행 방법을 정리한다.

실행 순서는 Database, Backend 순이다. Backend에 Spring Data JPA가 들어 있어서 접속할 DB가 없으면 부팅 단계에서 실패한다.

## 최초 1회 설정

### 1. .env 만들기

`.env.example`을 복사해 `.env`를 만들고 값을 채운다. 이 파일은 `docker-compose.yml`이 읽는다. 커밋하지 않는다.

```powershell
Copy-Item .env.example .env
```

### 2. Gradle에 DB 비밀번호 알려주기

Spring은 `.env`를 읽지 않는다. `.env`를 읽는 것은 Docker Compose의 기능이지 Java나 Spring의 기능이 아니다.

그래서 같은 비밀번호를 Gradle 쪽에도 한 번 더 알려줘야 한다. 저장소 밖인 사용자 홈에 두어 커밋될 일을 없앤다.

`C:\Users\<사용자>\.gradle\gradle.properties` 파일을 만들고 아래 한 줄을 넣는다. 값은 `.env`의 `POSTGRES_PASSWORD`와 같아야 한다.

```properties
POSTGRES_PASSWORD=여기에_비밀번호
```

`backend/build.gradle`이 이 값을 읽어 `test`와 `bootRun`에 환경변수로 넘긴다. 값이 없으면 접속 시 `password authentication failed`가 난다.

CI에는 이 파일이 없으므로 워크플로가 환경변수로 대신 넘긴다.

## Database 실행

```powershell
docker compose up -d
docker compose ps
```

`STATUS`가 `Up ... (healthy)`가 되면 접속을 받을 준비가 된 것이다. healthcheck가 5초 간격이라 처음에는 `(health: starting)`으로 잠깐 표시된다.

호스트 포트는 `docker-compose.yml`의 `ports` 설정을 따른다. 컨테이너 안은 5432지만 밖에서 붙을 때는 그 포트를 쓴다.

### 접속 확인

`<USER>`, `<DB>`는 `.env`에 넣은 `POSTGRES_USER`, `POSTGRES_DB` 값이다.

```powershell
docker exec -it ticketing-postgres psql -U <USER> -d <DB> -c "\l"
```

### 종료

```powershell
docker compose down
```

데이터까지 지우려면 `-v`를 붙인다. 볼륨이 삭제되므로 다음 실행 때 DB가 새로 초기화된다.

```powershell
docker compose down -v
```

## Backend 실행

DB가 떠 있는 상태에서 실행한다.

```powershell
cd backend
.\gradlew.bat bootRun
```

실행 후 Backend는 기본적으로 `8080` 포트를 사용한다.

Health API 확인:

```powershell
curl http://localhost:8080/api/health
```

## 테스트

테스트도 실제 DB에 접속하므로 컨테이너가 떠 있어야 한다.

```powershell
docker compose up -d
cd backend
.\gradlew.bat test
```

# 실행 방법

이 문서는 티켓 예매 시스템의 로컬 실행 방법을 정리한다.

실행 순서는 Database, Backend 순이다. Backend에 Spring Data JPA가 들어 있어서 접속할 DB가 없으면 부팅 단계에서 실패한다.

## Database 실행

### 최초 1회

`.env.example`을 복사해 `.env`를 만들고 값을 채운다. `.env`는 커밋하지 않는다.

```powershell
Copy-Item .env.example .env
```

### 실행

```powershell
docker compose up -d
docker compose ps
```

`STATUS`가 `Up ... (healthy)`가 되면 접속을 받을 준비가 된 것이다. healthcheck가 5초 간격이라 처음에는 `(health: starting)`으로 잠깐 표시된다.

호스트 포트는 `docker-compose.yml`의 `ports` 설정을 따른다.

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

> Spring datasource 접속 설정은 Issue #10에서 추가한다. 그 전까지는 DB를 띄워도 `bootRun`이 DataSource 오류로 실패한다.

프로젝트 루트에서 Backend 디렉토리로 이동한 뒤 Spring Boot 애플리케이션을 실행한다.

```powershell
cd backend
.\gradlew.bat bootRun
```

실행 후 Backend는 기본적으로 `8080` 포트를 사용한다.

Health API 확인:

```powershell
curl http://localhost:8080/api/health
```

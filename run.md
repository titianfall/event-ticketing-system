# 실행 방법

이 문서는 티켓 예매 시스템의 로컬 실행 방법을 정리한다.

## Backend 실행

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

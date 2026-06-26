# Issue #3. Spring Boot Backend 생성

이 문서는 Issue #3을 진행하면서 생성한 Spring Boot backend 기본 구조와 실행 흐름을 이해하기 위한 학습 노트다.

## 목적

Spring Boot Backend 기본 프로젝트를 생성하고, 최소 실행 가능한 API 서버 구조를 만든다.

이번 이슈의 핵심은 기능 구현이 아니라 다음 두 가지를 확인하는 것이다.

- Backend 애플리케이션이 실행된다.
- `GET /api/health` 요청에 정상 응답한다.

## 작업 범위

이번 이슈에서 처음으로 `backend/` 디렉토리를 생성한다.

이번 이슈에서 하는 일:

- Spring Initializr로 Spring Boot 프로젝트 생성
- Gradle 기반 backend 프로젝트 구조 추가
- Spring Boot 메인 클래스 확인
- `GET /api/health` Health API 추가
- 로컬에서 backend 실행 확인

이번 이슈에서 하지 않는 일:

- JPA 설정
- MySQL 연결
- Spring Security 설정
- 회원가입 / 로그인 구현
- 예매 도메인 구현
- Redis, Kafka 설정
- GitHub Actions CI 설정

## Spring Initializr 설정

Spring Initializr에서는 다음 값으로 backend 프로젝트를 생성한다.

```text
Project: Gradle - Groovy
Language: Java
Spring Boot: 안정 버전
Packaging: Jar
Java: 17
Group: com.example
Artifact: ticketing
Name: ticketing
Package name: com.example.ticketing
Dependencies:
- Spring Web
```

아직 추가하지 않는 의존성:

```text
Spring Data JPA
MySQL Driver
Spring Security
Validation
Redis
Kafka
```

위 의존성들은 이후 마일스톤에서 필요할 때 추가한다. 이번 단계에서 미리 넣으면 DB 연결, 보안 설정 등 아직 다루지 않을 설정 때문에 실행 흐름이 복잡해질 수 있다.

## backend 디렉토리 배치

Spring Initializr에서 받은 `ticketing` 폴더 자체를 프로젝트 루트의 `backend`로 사용한다.

정상 구조:

```text
D:\event-ticketing-system\backend\build.gradle
D:\event-ticketing-system\backend\settings.gradle
D:\event-ticketing-system\backend\gradlew.bat
D:\event-ticketing-system\backend\gradle\
D:\event-ticketing-system\backend\src\
```

잘못된 구조:

```text
D:\event-ticketing-system\backend\ticketing\build.gradle
```

`build.gradle`은 `backend/` 바로 아래에 있어야 한다.

## JDK 환경

Spring Boot 개발에는 JRE가 아니라 JDK가 필요하다.

```text
JRE: Java 실행만 가능
JDK: Java 컴파일과 실행 가능
```

이 프로젝트에서는 JDK 17 LTS를 기준으로 맞춘다.

확인 명령:

```powershell
java -version
javac -version
where.exe java
where.exe javac
echo $env:JAVA_HOME
```

정상 예시:

```text
openjdk version "17.0.19"
javac 17.0.19
```

`JAVA_HOME`에는 `bin`을 붙이지 않는다.

```text
JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot
Path      = %JAVA_HOME%\bin
```

`where.exe java` 결과에서 Temurin JDK 17 경로가 Oracle Java 경로보다 먼저 나오면 정상이다.

## VS Code Java 설정 메모

VS Code에서 Java 자동 import와 컴파일 지원을 받으려면 Java 확장이 JDK를 인식해야 한다.

권장 확장:

```text
Extension Pack for Java
Spring Boot Extension Pack
```

자동 import Quick Fix 단축키:

```text
Ctrl + .
```

Java 언어 서버가 꼬였을 때:

```text
Ctrl + Shift + P
Java: Clean Java Language Server Workspace
```

탐색기에서 `java \ com \ example \ ticketing`처럼 폴더가 한 줄로 합쳐 보이면 VS Code의 Compact Folders 설정 때문이다.

끄는 설정:

```json
{
  "explorer.compactFolders": false
}
```

## 생성된 주요 파일

### build.gradle

Gradle 빌드 설정 파일이다.

프로젝트에서 사용할 플러그인, Java 버전, 의존성, 테스트 설정을 관리한다.

### settings.gradle

Gradle 프로젝트 이름을 정의하는 파일이다.

단일 모듈 프로젝트에서는 보통 프로젝트 이름만 설정한다.

### gradlew / gradlew.bat

Gradle Wrapper 실행 파일이다.

로컬에 Gradle이 설치되어 있지 않아도 프로젝트가 지정한 Gradle 버전으로 빌드할 수 있게 한다.

- `gradlew`: macOS/Linux용
- `gradlew.bat`: Windows용

Windows에서는 다음처럼 실행한다.

```powershell
.\gradlew.bat bootRun
```

### gradle/wrapper/

Gradle Wrapper가 사용할 Gradle 배포판 정보를 담는다.

Gradle Wrapper 관련 파일은 커밋 대상이다.

### src/main/java

애플리케이션 Java 코드가 위치하는 디렉토리다.

현재 기본 패키지는 다음과 같다.

```java
package com.example.ticketing;
```

파일 경로와 패키지명은 대응된다.

```text
src/main/java/com/example/ticketing
-> package com.example.ticketing;
```

### src/main/resources

애플리케이션 설정 파일이 위치하는 디렉토리다.

### application.properties 또는 application.yml

Spring Boot 설정 파일이다.

서버 포트, 애플리케이션 이름, DB 연결 정보 등을 설정할 수 있다.

이번 이슈에서는 DB를 연결하지 않는다.

### .gitattributes

Git에서 줄바꿈과 파일 처리 방식을 관리하는 설정 파일이다.

특히 `gradlew` 같은 스크립트 파일 관리에 도움이 된다.

### .gitignore

Git에 포함하지 않을 파일을 정의한다.

예:

```text
build/
.gradle/
IDE 설정
임시 파일
```

루트 `.gitignore`와 `backend/.gitignore`가 함께 있어도 문제는 없다.

### HELP.md

Spring Initializr가 생성한 안내 문서다.

필수 파일은 아니며, 유지하거나 삭제할 수 있다. 실무 프로젝트에서는 보통 README로 통합하거나 삭제하는 경우가 많다.

## TicketingApplication.java

파일 위치:

```text
backend/src/main/java/com/example/ticketing/TicketingApplication.java
```

코드:

```java
package com.example.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);
    }
}
```

이 파일은 Spring Boot 애플리케이션의 시작점이다.

### package

```java
package com.example.ticketing;
```

이 클래스가 `com.example.ticketing` 패키지에 속한다는 뜻이다.

파일 위치와 패키지명은 맞아야 한다.

```text
src/main/java/com/example/ticketing/TicketingApplication.java
package com.example.ticketing;
```

### import

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
```

`SpringApplication`은 Spring Boot 애플리케이션을 실행하는 클래스다.

`SpringBootApplication`은 이 클래스가 Spring Boot의 메인 설정 클래스라는 뜻을 가진 어노테이션이다.

### @SpringBootApplication

```java
@SpringBootApplication
```

이 어노테이션은 Spring Boot 애플리케이션의 핵심 설정을 켠다.

대표적으로 다음 역할을 포함한다고 이해하면 된다.

```text
Component Scan
Auto Configuration
Configuration Class
```

중요한 점은 현재 패키지인 `com.example.ticketing`을 기준으로 하위 패키지를 스캔한다는 것이다.

따라서 다음 위치에 만드는 클래스들은 Spring이 자동으로 찾을 수 있다.

```text
com.example.ticketing.global
com.example.ticketing.domain
com.example.ticketing.infra
```

반대로 `com.example.ticketing` 바깥 패키지에 클래스를 만들면 Spring이 자동으로 못 찾을 수 있다.

### main 메서드

```java
public static void main(String[] args) {
    SpringApplication.run(TicketingApplication.class, args);
}
```

`main()`은 Java 프로그램의 시작 지점이다.

`SpringApplication.run()`을 호출하면 Spring Boot가 다음 일을 수행한다.

```text
1. Spring 애플리케이션 컨텍스트 생성
2. 설정 자동 구성
3. Controller, Service, Component 등 Bean 스캔
4. 내장 Tomcat 실행
5. HTTP 요청을 받을 준비 완료
```

`Spring Web` 의존성이 있기 때문에 별도 Tomcat 설치 없이 `bootRun`으로 웹 서버가 실행된다.

## HealthController.java

파일 위치:

```text
backend/src/main/java/com/example/ticketing/global/common/HealthController.java
```

코드:

```java
package com.example.ticketing.global.common;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
```

이 클래스는 서버가 정상 실행 중인지 확인하기 위한 가장 작은 API 컨트롤러다.

### package

```java
package com.example.ticketing.global.common;
```

이 클래스가 `com.example.ticketing.global.common` 패키지에 속한다는 뜻이다.

`TicketingApplication`이 있는 `com.example.ticketing`의 하위 패키지이므로 Spring이 자동으로 스캔할 수 있다.

### import java.util.Map

```java
import java.util.Map;
```

응답 데이터를 간단한 key-value 형태로 만들기 위해 Java의 `Map`을 사용한다.

이번 API에서는 다음 JSON을 만들기 위해 사용한다.

```json
{
  "status": "UP"
}
```

### Spring Web import

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
```

이 세 어노테이션은 Spring Web이 제공한다.

- `@RestController`: 이 클래스가 REST API 요청을 처리하는 컨트롤러임을 의미한다.
- `@RequestMapping`: 이 컨트롤러의 공통 URL prefix를 지정한다.
- `@GetMapping`: HTTP GET 요청을 처리할 메서드를 지정한다.

### @RestController

```java
@RestController
```

이 클래스가 HTTP 요청을 처리하는 REST 컨트롤러라는 뜻이다.

메서드가 반환한 값은 문자열 화면이 아니라 HTTP 응답 body로 변환된다.

`Map`을 반환하면 Spring Boot가 JSON으로 변환해서 응답한다.

### @RequestMapping("/api")

```java
@RequestMapping("/api")
```

이 컨트롤러의 기본 경로를 `/api`로 지정한다.

즉, 이 클래스 안의 API들은 기본적으로 `/api` 아래에 위치한다.

### @GetMapping("/health")

```java
@GetMapping("/health")
```

HTTP GET 요청 중 `/health` 경로를 처리한다.

클래스의 `@RequestMapping("/api")`와 합쳐져 최종 API 주소는 다음과 같다.

```text
GET /api/health
```

### health 메서드

```java
public Map<String, String> health() {
    return Map.of("status", "UP");
}
```

`Map<String, String>`은 key와 value가 모두 문자열인 Map을 반환한다는 뜻이다.

`Map.of("status", "UP")`은 다음 데이터를 만든다.

```text
status -> UP
```

Spring Boot는 이 Map을 JSON으로 변환한다.

응답 예시:

```json
{
  "status": "UP"
}
```

## 실행 확인

backend 디렉토리로 이동한다.

```powershell
cd backend
```

Spring Boot 애플리케이션을 실행한다.

```powershell
.\gradlew.bat bootRun
```

다른 PowerShell 창에서 Health API를 확인한다.

```powershell
Invoke-RestMethod http://localhost:8080/api/health
```

정상 응답:

```json
{
  "status": "UP"
}
```

브라우저에서도 확인할 수 있다.

```text
http://localhost:8080/api/health
```

## Git 확인

Issue #3 작업 브랜치 예시:

```text
issue/3-init-backend
```

상태 확인:

```powershell
git status --short --branch
```

커밋에 포함하면 안 되는 대표 파일:

```text
backend/build/
backend/.gradle/
```

Gradle Wrapper 파일은 커밋 대상이다.

```text
backend/gradlew
backend/gradlew.bat
backend/gradle/wrapper/gradle-wrapper.jar
backend/gradle/wrapper/gradle-wrapper.properties
```

## 완료 조건

- Backend 애플리케이션이 실행된다.
- `GET /api/health` 요청에 정상 응답한다.

## 추천 커밋 메시지

```bash
feat: initialize spring boot backend
```

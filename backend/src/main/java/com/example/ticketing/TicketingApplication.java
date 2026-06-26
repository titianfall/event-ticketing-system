package com.example.ticketing; // Spring Book 서버가 실행되는 곳

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringApplication은 Spring Boot 애플리케이션을 실행하는 클래스라는 뜻이다.
// SpringBootApplication은 Spring Boot의 메인 설정 클래스라는 뜻이다.
@SpringBootApplication // 하위 패키지 스캔, 설정 자동 구성, 설정 클래스 역할로 지정
public class TicketingApplication {
	// JVM이 애플리케이션 실행시 가장 먼저 찾는 진입점
	public static void main(String[] args) { 
		// Spring Boot 애플리케이션 실행 + .\gradlew.bat bootRun(별도 서버 설치 x)
		SpringApplication.run(TicketingApplication.class, args);
	}

}

package com.example.ticketing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 테스트에서 주입받을 수 있도록
class TicketingApplicationTests {

	@Autowired
	MockMvc mockMvc; // servlet, jakarta 차이?

	@Test
	void contextLoads() {
	}

	@Test
	void healthReturnsUp() throws Exception {
		// 일반 import 시
		// MockMvcRequestBuilders.get
		// MockMvcResultMatchers.status().isOk()
		// MockMvcResultMatchers.jsonPath().value()

		// import static *
		// 테스트 내부에서 url 요청 흉내
		// 응답 상태가 200인지, JSON 안의 status 값이 UP인지 확인
		mockMvc.perform(get("/api/health")) 
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("UP"));
	}	

}

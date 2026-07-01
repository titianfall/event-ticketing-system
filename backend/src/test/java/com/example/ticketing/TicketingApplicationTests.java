package com.example.ticketing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
		// given
		String healthCheckPath = "/api/health";

		// when
		ResultActions resultActions = mockMvc.perform(get(healthCheckPath));

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("UP"));
	}	

}

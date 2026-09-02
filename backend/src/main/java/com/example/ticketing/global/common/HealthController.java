package com.example.ticketing.global.common;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketing.global.response.ApiResponse;

@RestController
@RequestMapping("/api")
public class HealthController {

    /** 서블릿이 살아 있는지만 확인한다. DB 상태는 보지 않는다. */
    @GetMapping("/health")
    public ApiResponse<Map<String, String>> health() {
        return ApiResponse.success(Map.of("status", "UP"));
    }
}

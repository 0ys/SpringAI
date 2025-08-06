package com.example.springai.openai;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClientCustomizer springAiRequestLogger() {
        return builder -> builder.requestInterceptor((request, body, execution) -> {
            System.out.println("📡 [Spring AI 요청 정보]");
            System.out.println("👉 URL: " + request.getURI());
            System.out.println("👉 Method: " + request.getMethod());
            System.out.println("👉 Headers:");
            request.getHeaders().forEach((k, v) -> System.out.println("   " + k + ": " + v));
            System.out.println("👉 Body:");
            System.out.println(new String(body, StandardCharsets.UTF_8));
            return execution.execute(request, body);
        });
    }
}


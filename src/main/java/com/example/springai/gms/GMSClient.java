package com.example.springai.gms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GMSClient {

    @Value("${gms.api.key}")
    private String gmsApiKey;

    @Value("${gms.base-url}")
    private String gmsBaseUrl;

    @Value("${gms.model}")
    private String gmsModel;

    @Value("${gms.temperature}")
    private String gmsTemperature;

    private final RestTemplate restTemplate = new RestTemplate();

    public String chat(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(gmsApiKey);

        String json = String.format("""
{
  "model": "%s",
  "temperature": %s,
  "messages": [
    {
      "role": "user",
      "content": %s
    }
  ]
}
""", gmsModel, gmsTemperature, toJsonString(userMessage));

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    gmsBaseUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode choices = root.path("choices");

            if (choices.isArray() && choices.size() > 0) {
                return choices.get(0).path("message").path("content").asText();
            } else {
                return "⚠️ GPT 응답에서 결과를 찾을 수 없습니다.";
            }

        } catch (HttpClientErrorException e) {
            return "❌ 요청 실패: " + e.getStatusCode() + "\n" + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "❌ 예외 발생: " + e.getMessage();
        }
    }

    private String toJsonString(String text) {
        return "\"" + text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r") + "\"";
    }
}

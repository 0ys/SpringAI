package com.example.springai.openai;

import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ChatConfig {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    @Value("${spring.ai.openai.chat.options.temperature}")
    private String temperature;

    @Bean
    public OpenAiChatModel openAiChatModel() {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .apiKey(apiKey)
                .build();

//        OpenAiApi openAiApi = new OpenAiApi(
//                baseUrl,
//                new SimpleApiKey(apiKey),
//                new LinkedMultiValueMap<>(),
//                "/chat/completions",
//                "/embeddings",
//                RestClient.builder(),
//                WebClient.builder(),
//                new DefaultResponseErrorHandler()
//        );

        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .model(model)
                .temperature(Double.parseDouble(temperature))
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(openAiChatOptions)
                .build();
    }
}


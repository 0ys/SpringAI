package com.example.springai.openai;

import com.example.springai.gms.GMSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final OpenAiChatModel chatModel;
    private final GMSClient gmsClient;

    public String chat(String userMessage) {
        Prompt prompt = new Prompt(List.of(
                new SystemMessage("You are a helpful assistant."),
                new UserMessage(userMessage)
        ));
        try {
            ChatResponse chatResponse = chatModel.call(prompt);
            return chatResponse.getResults().get(0).getOutput().getText();
        } catch (HttpClientErrorException e) {
            return "❌ 요청 실패: " + e.getStatusCode() + "\n" + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "❌ 예외 발생: " + e.getMessage();
        }

    }

    public String ssafyChat(String userMessage) {
        return gmsClient.chat(userMessage);
    }
}
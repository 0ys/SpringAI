package com.example.springai.openai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final OpenAiChatModel chatModel;

    public String chat(String userMessage) {
        Prompt prompt = new Prompt(List.of(
                new SystemMessage("You are a helpful assistant."),
                new UserMessage(userMessage)
        ));
        ChatResponse chatResponse = chatModel.call(prompt);

        return chatResponse.getResults().get(0).getOutput().getText();
    }
}
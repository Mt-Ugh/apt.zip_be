package com.aptzip.chatbot.service;

import com.aptzip.chatbot.dto.response.ChatBotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public ChatBotResponse chat(List<Map<String, String>> messages) {
        Map<String, String> systemMessage = Map.of(
                "role", "system",
                "content", "당신은 대한민국 부동산 전문가 챗봇입니다. 정확하고 친절하게 답변해주세요."
        );

        List<Map<String, String>> enhancedMessages = new ArrayList<>();
        enhancedMessages.add(systemMessage);
        enhancedMessages.addAll(messages);

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", enhancedMessages,
                "max_tokens", 500,
                "temperature", 0.7
        );

        ChatBotResponse response = webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + openaiApiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ChatBotResponse.class)
                .block();

        // 인사말 붙이기 위해 메시지 내용 가져오기
        ChatBotResponse.Message assistantMessage = response.choices().get(0).message();
        String modifiedContent = "안녕하세요 APT.ZIP 의 챗봇입니다!\n\n" + assistantMessage.content();

        // 새로운 Message, Choice 리스트로 다시 감싸기
        ChatBotResponse.Message newMessage = new ChatBotResponse.Message(assistantMessage.role(), modifiedContent);
        ChatBotResponse.Choice newChoice = new ChatBotResponse.Choice(newMessage);

        return new ChatBotResponse(List.of(newChoice));
    }
}


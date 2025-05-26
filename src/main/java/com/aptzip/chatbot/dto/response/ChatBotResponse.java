package com.aptzip.chatbot.dto.response;

import java.util.List;

public record ChatBotResponse(List<Choice> choices) {
    public record Choice(Message message) {}
    public record Message(String role, String content) {}
}

package com.aptzip.chatbot.dto.request;

import java.util.List;
import java.util.Map;

public record ChatBotRequest(
        List<Map<String, String>> messages) {
}

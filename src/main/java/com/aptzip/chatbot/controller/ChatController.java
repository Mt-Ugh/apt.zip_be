package com.aptzip.chatbot.controller;

import com.aptzip.chatbot.dto.request.ChatBotRequest;
import com.aptzip.chatbot.dto.response.ChatBotResponse;
import com.aptzip.chatbot.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatBotService chatBotService;

    @PostMapping("/chatbot")
    public ResponseEntity<ChatBotResponse> chat(@RequestBody ChatBotRequest request) {
        ChatBotResponse reply = chatBotService.chat(request.messages());
        return ResponseEntity.ok().body(reply);
    }
}

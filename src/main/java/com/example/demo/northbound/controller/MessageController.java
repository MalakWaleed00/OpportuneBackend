package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.messages.ConversationDTO;
import com.example.demo.domain.dto.messages.MessageResponseDTO;
import com.example.demo.domain.dto.messages.SendMessageRequestDTO;
import com.example.demo.service.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final IMessageService messageService;

    @GetMapping("/conversations")
    public List<ConversationDTO> getUserConversations() {
        return messageService.getUserConversations();
    }

    @GetMapping("/conversations/{conversationId}")
    public List<MessageResponseDTO> getConversationMessages(
            @PathVariable Long conversationId
    ) {
        return messageService.getConversationMessages(conversationId);
    }

    @PostMapping
    public MessageResponseDTO sendMessage(
            @RequestBody SendMessageRequestDTO request
    ) {
        return messageService.sendMessage(request);
    }
}
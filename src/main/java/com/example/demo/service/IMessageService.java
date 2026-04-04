package com.example.demo.service;

import com.example.demo.domain.dto.messages.ConversationDTO;
import com.example.demo.domain.dto.messages.MessageResponseDTO;
import com.example.demo.domain.dto.messages.SendMessageRequestDTO;

import java.util.List;

public interface IMessageService {
    List<ConversationDTO> getUserConversations();

    List<MessageResponseDTO> getConversationMessages(Long conversationId);

    MessageResponseDTO sendMessage(SendMessageRequestDTO request);
}

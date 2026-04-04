package com.example.demo.service.impl;

import com.example.demo.common.util.IAuthUtils;
import com.example.demo.domain.dto.messages.ConversationDTO;
import com.example.demo.domain.dto.messages.MessageResponseDTO;
import com.example.demo.domain.dto.messages.SendMessageRequestDTO;
import com.example.demo.repository.IConversationRepository;
import com.example.demo.repository.IMessageRepository;
import com.example.demo.service.IMessageService;
import com.example.demo.southbound.mapper.MessageMapper;
import com.example.demo.southbound.entity.Conversation;
import com.example.demo.southbound.entity.Message;
import com.example.demo.southbound.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {

    private final IMessageRepository messageRepository;
    private final IConversationRepository conversationRepository;
    private final IAuthUtils authUtils;
    private final MessageMapper messageMapper;

    @Override
    public List<ConversationDTO> getUserConversations() {

        Long userId = authUtils.getCurrentUserId();

        List<Conversation> conversations =
                conversationRepository.findByParticipants_Id(userId);

        return conversations.stream().map(conv -> {

            User otherUser = conv.getParticipants()
                    .stream()
                    .filter(u -> !u.getId().equals(userId))
                    .findFirst()
                    .orElse(null);

            Message lastMessage = messageRepository
                    .findTopByConversation_IdOrderBySentAtDesc(conv.getId())
                    .orElse(null);

            return ConversationDTO.builder()
                    .conversationId(conv.getId())
                    .otherUserName(otherUser != null ? otherUser.getName() : "Unknown")
                    .lastMessage(lastMessage != null ? lastMessage.getContent() : "")
                    .lastMessageTime(lastMessage != null ? lastMessage.getSentAt() : null)
                    .build();

        }).toList();
    }

    @Override
    public List<MessageResponseDTO> getConversationMessages(Long conversationId) {

        return messageRepository
                .findByConversationIdOrderBySentAtAsc(conversationId)
                .stream()
                .map(messageMapper::toDTO)
                .toList();
    }

    @Override
    public MessageResponseDTO sendMessage(SendMessageRequestDTO request) {

        User sender = authUtils.getCurrentUser();

        Conversation conversation =
                conversationRepository.findById(request.getConversationId())
                        .orElseThrow(() -> new RuntimeException("Conversation not found"));

        Message message = Message.builder()
                .content(request.getContent())
                .sentAt(LocalDateTime.now())
                .sender(sender)
                .conversation(conversation)
                .read(false)
                .build();

        messageRepository.save(message);

        return messageMapper.toDTO(message);
    }
}
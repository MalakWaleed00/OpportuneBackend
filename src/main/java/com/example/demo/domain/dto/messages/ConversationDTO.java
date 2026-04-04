package com.example.demo.domain.dto.messages;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ConversationDTO {

    private Long conversationId;

    private String otherUserName;

    private String lastMessage;

    private LocalDateTime lastMessageTime;
}

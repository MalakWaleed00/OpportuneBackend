package com.example.demo.domain.dto.messages;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessageResponseDTO {

    private Long id;

    private Long senderId;

    private String senderName;

    private String content;

    private LocalDateTime sentAt;

    private boolean read;
}

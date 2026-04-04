package com.example.demo.domain.dto.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageRequestDTO {

    private Long conversationId;

    private String content;
}

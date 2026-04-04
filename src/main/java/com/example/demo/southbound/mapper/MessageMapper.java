package com.example.demo.southbound.mapper;

import com.example.demo.domain.dto.messages.MessageResponseDTO;
import com.example.demo.southbound.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.name", target = "senderName")
    MessageResponseDTO toDTO(Message message);
}

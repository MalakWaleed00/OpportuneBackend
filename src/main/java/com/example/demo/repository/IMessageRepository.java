package com.example.demo.repository;

import com.example.demo.southbound.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IMessageRepository
        extends JpaRepository<Message, Long> {

    List<Message> findByConversationIdOrderBySentAtAsc(Long conversationId);

    Optional<Message> findTopByConversation_IdOrderBySentAtDesc(Long conversationId);
}

package com.example.demo.repository;

import com.example.demo.southbound.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConversationRepository
        extends JpaRepository<Conversation, Long> {

    List<Conversation> findByParticipants_Id(Long userId);
}

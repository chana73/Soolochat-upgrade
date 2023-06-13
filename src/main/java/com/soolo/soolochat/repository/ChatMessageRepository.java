package com.soolo.soolochat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soolo.soolochat.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {


	/*@Query("SELECT m FROM ChatMessage m WHERE m.chatRoom.chatRoomId = :chatRoomId ORDER BY m.createdAt DESC")
	Page<ChatMessage> findLatestMessageBychatRoomId(@Param("chatRoomId") Long chatRoomId, Pageable pageable);*/
	Page<ChatMessage> findByisDeletedFalseAndChatRoomChatRoomIdOrderByCreatedAtDesc(Long chatRoomId, Pageable pageable);
}




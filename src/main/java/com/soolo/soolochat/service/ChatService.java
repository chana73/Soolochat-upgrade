package com.soolo.soolochat.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.soolo.soolochat.entity.ChatMessage;
import com.soolo.soolochat.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatService
 * @author        : mycom
 * @since         : 2023-05-29
 */
@EnableScheduling
@RequiredArgsConstructor
@Service
public class ChatService {

	private final RedisTemplate<String, ChatMessage> redisTemplate;
	private final ChatMessageRepository chatMessageRepository;

	public void save(ChatMessage message) {

		redisTemplate.opsForList().rightPush(message.getChatRoomUniqueId().toString(), message);
	}

	@Scheduled(fixedRate  = 60000) // 1분마다 실행
	public void saveMessagesToDb() {
		Set<String> roomIdKeys = redisTemplate.keys("*"); // Redis에 저장된 모든 채팅방 키를 가져옴

		for (String roomIdKey : roomIdKeys) {
			List<ChatMessage> chatMessages = redisTemplate.opsForList().range(roomIdKey, 0, -1);
			chatMessageRepository.saveAll(chatMessages);
			redisTemplate.delete(roomIdKey);
		}
	}


}

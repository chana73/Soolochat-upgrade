package com.soolo.soolochat.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.soolo.soolochat.connenct.PartyParticipate;
import com.soolo.soolochat.dto.response.ChatMessageList;
import com.soolo.soolochat.dto.response.ChatMessageListResponse;
import com.soolo.soolochat.dto.response.ChatMessageResponse;
import com.soolo.soolochat.dto.response.ChatRoomListDto;
import com.soolo.soolochat.dto.ResponseDto;
import com.soolo.soolochat.dto.request.ChatMessageRequest;
import com.soolo.soolochat.dto.request.ChatMessageListRequest;
import com.soolo.soolochat.dto.request.ChatRoomListRequest;
import com.soolo.soolochat.entity.ChatMessage;
import com.soolo.soolochat.entity.ChatRoom;
import com.soolo.soolochat.repository.ChatMessageRepository;
import com.soolo.soolochat.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatController
 * @author        : mycom
 * @since         : 2023-05-27
 */
@RequiredArgsConstructor
@RestController
public class ChatController {

	private final SimpMessageSendingOperations messagingTemplate;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageRepository chatMessageRepository;

	@Transactional
	@MessageMapping("/chat/chatList/{memberUniqueId}")
	public void chatRoomList(@DestinationVariable("memberUniqueId") String memberUniqueId) {

		List<PartyParticipate> partyParticipateList = chatRoomRepository.findByMemberId(memberUniqueId, PageRequest.of(0, 1));
		List<ChatRoomListDto> chatRoomsList = new ArrayList<>();
		for(PartyParticipate partyParticipate : partyParticipateList){
			ChatRoomListDto chatRoomListDto = new ChatRoomListDto(partyParticipate);
			chatRoomsList.add(chatRoomListDto);
		}
		ResponseDto responseDto = ResponseDto.setSuccess(200, "채팅방 조회 성공", chatRoomsList);
		messagingTemplate.convertAndSend("/sub/chat/chatList/" + memberUniqueId, responseDto);
	}

	@Transactional
	@MessageMapping("/chat/messageList/{memberUniqueId}")
	public void chatMessageList(@DestinationVariable("memberUniqueId") String memberUniqueId, ChatMessageListRequest chatMessageList) {

		//Pageable pageable = PageRequest.of(chatMessageList.getPage(), 10, Sort.by("createdAt").descending());
		Pageable pageable = PageRequest.of(chatMessageList.getPage(), 10);
		Page<ChatMessage> chatMessages = chatMessageRepository.findLatestMessageBychatRoomId(chatMessageList.getChatRoomId(), pageable);
		List<ChatMessageList> chatMessageLists = new ArrayList<>();
		for(ChatMessage chatMessage : chatMessages){
			ChatMessageList messageList = new ChatMessageList(chatMessage);
			chatMessageLists.add(messageList);
		}
		ChatMessageListResponse chatMessageListResponse = new ChatMessageListResponse(chatMessageLists, chatMessageList.getPage(), chatMessages.getTotalPages());
		ResponseDto responseDto = ResponseDto.setSuccess(200, "메세지 불러오기 성공", chatMessageListResponse);
		messagingTemplate.convertAndSend("/sub/chat/messageList/" + memberUniqueId, responseDto);
	}

	@Transactional
	@MessageMapping("/chat/message/{chatRoomUniqueId}")
	public void message(@DestinationVariable("chatRoomUniqueId") String chatRoomUniqueId,ChatMessageRequest chatMessageRequest) {

		ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(chatRoomUniqueId);
		LocalDateTime createdAt = LocalDateTime.now();
		ChatMessageResponse chatMessageResponse = new ChatMessageResponse(chatMessageRequest, createdAt);
		ResponseDto responseDto = ResponseDto.setSuccess(200, "메세지 전송 성공", chatMessageResponse);

		ChatMessage chatMessage = new ChatMessage(chatMessageRequest, chatRoom, createdAt);
		chatMessageRepository.save(chatMessage);
		messagingTemplate.convertAndSend("/sub/chat/message/" + chatRoomUniqueId, responseDto);

	}
}

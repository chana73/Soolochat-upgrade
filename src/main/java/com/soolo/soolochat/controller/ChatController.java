package com.soolo.soolochat.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soolo.soolochat.connenct.Member;
import com.soolo.soolochat.connenct.PartyParticipate;
import com.soolo.soolochat.dto.response.ChatMessageList;
import com.soolo.soolochat.dto.response.ChatMessageListResponse;
import com.soolo.soolochat.dto.response.ChatMessageResponse;
import com.soolo.soolochat.dto.response.ChatRoomListDto;
import com.soolo.soolochat.dto.ResponseDto;
import com.soolo.soolochat.dto.request.ChatMessageRequest;
import com.soolo.soolochat.dto.request.ChatMessageListRequest;
import com.soolo.soolochat.dto.request.ChatRoomListRequest;
import com.soolo.soolochat.entity.ChatCount;
import com.soolo.soolochat.entity.ChatMessage;
import com.soolo.soolochat.entity.ChatRoom;
import com.soolo.soolochat.repository.ChatCountRepository;
import com.soolo.soolochat.repository.ChatMessageRepository;
import com.soolo.soolochat.repository.ChatRoomRepository;
import com.soolo.soolochat.repository.PartyParticipateRepository;
import com.soolo.soolochat.service.ChatService;

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
	private final PartyParticipateRepository partyParticipateRepository;
	private final ChatCountRepository chatCountRepository;
	private final ChatService chatService;

	@Transactional
	@MessageMapping("/chat/chatList/{memberUniqueId}")
	public void chatRoomList(@DestinationVariable("memberUniqueId") String memberUniqueId) {

		List<ChatRoomListDto> chatRoomsList = getList(memberUniqueId);
		ResponseDto responseDto = ResponseDto.setSuccess(200, "채팅방 조회 성공", chatRoomsList);
		//sub주소로 메세지를 보내는
		messagingTemplate.convertAndSend("/sub/chat/chatList/" + memberUniqueId, responseDto);
	}

	/*@Transactional
	@MessageMapping("/chat/messageList/{memberUniqueId}")
	public void chatMessageList(@DestinationVariable("memberUniqueId") String memberUniqueId, ChatMessageListRequest chatMessageList) {

		PartyParticipate partyParticipate = partyParticipateRepository.findByisDeletedFalseAndMemberMemberUniqueIdAndChatRoomChatRoomUniqueId(memberUniqueId,chatMessageList.getChatRoomUniqueId());
		List<ChatCount> chatCounts = chatCountRepository.findByisDeletedFalseAndReadStatusFalseAndPartyParticipate(partyParticipate);
		for (ChatCount chatCount : chatCounts) {
			chatCount.setReadStatus(true);
		}
		//Pageable pageable = PageRequest.of(chatMessageList.getPage(), 10, Sort.by("createdAt").descending());
		Pageable pageable = PageRequest.of(chatMessageList.getPage(), 10);
		Page<ChatMessage> chatMessages = chatMessageRepository.findByisDeletedFalseAndChatRoomChatRoomIdOrderByCreatedAtDesc(chatMessageList.getChatRoomId(), pageable);
		List<ChatMessageList> chatMessageLists = new ArrayList<>();
		for(ChatMessage chatMessage : chatMessages){

			ChatMessageList messageList = new ChatMessageList(chatMessage);
			chatMessageLists.add(messageList);
		}
		ChatMessageListResponse chatMessageListResponse = new ChatMessageListResponse(chatMessageLists, chatMessageList.getPage(), chatMessages.getTotalPages()-1);
		ResponseDto responseDto = ResponseDto.setSuccess(200, "메세지 불러오기 성공", chatMessageListResponse);
		messagingTemplate.convertAndSend("/sub/chat/messageList/" + memberUniqueId, responseDto);
	}*/

	/*
	private Long chatRoomId;
	private String chatRoomUniqueId;
	private int page;   */
	@Transactional
	@GetMapping("/chat/messageList/{memberUniqueId}")
	// public ResponseEntity<ResponseDto> findAll(@PathVariable String memberUniqueId, @RequestBody ChatMessageListRequest chatMessageList) {
	public ResponseEntity<ResponseDto> findAll(@PathVariable String memberUniqueId, @RequestParam Long chatRoomId, @RequestParam String chatRoomUniqueId, @RequestParam int page){
		// ChatMessageListRequest chatMessageList = new ChatMessageListRequest(chatRoomId, chatRoomUniqueId, page);
		// PartyParticipate partyParticipate = partyParticipateRepository.findByisDeletedFalseAndMemberMemberUniqueIdAndChatRoomChatRoomUniqueId(memberUniqueId,chatMessageList.getChatRoomUniqueId());
		PartyParticipate partyParticipate = partyParticipateRepository.findByisDeletedFalseAndMemberMemberUniqueIdAndChatRoomChatRoomUniqueId(memberUniqueId,chatRoomUniqueId);
		List<ChatCount> chatCounts = chatCountRepository.findByisDeletedFalseAndReadStatusFalseAndPartyParticipate(partyParticipate);
		for (ChatCount chatCount : chatCounts) {
			chatCount.setReadStatus(true);
		}
		//Pageable pageable = PageRequest.of(chatMessageList.getPage(), 10, Sort.by("createdAt").descending());
		// Pageable pageable = PageRequest.of(chatMessageList.getPage(), 10);
		Pageable pageable = PageRequest.of(page, 10);
		// Page<ChatMessage> chatMessages = chatMessageRepository.findByisDeletedFalseAndChatRoomChatRoomIdOrderByCreatedAtDesc(chatMessageList.getChatRoomId(), pageable);
		Page<ChatMessage> chatMessages = chatMessageRepository.findByisDeletedFalseAndChatRoomChatRoomIdOrderByCreatedAtDesc(chatRoomId, pageable);
		List<ChatMessageList> chatMessageLists = new ArrayList<>();
		for(ChatMessage chatMessage : chatMessages){
			ChatMessageList messageList = new ChatMessageList(chatMessage);
			chatMessageLists.add(messageList);
		}
		// ChatMessageListResponse chatMessageListResponse = new ChatMessageListResponse(chatMessageLists, chatMessageList.getPage(), chatMessages.getTotalPages()-1);
		ChatMessageListResponse chatMessageListResponse = new ChatMessageListResponse(chatMessageLists, page, chatMessages.getTotalPages()-1);
		return new ResponseEntity<>(new ResponseDto(200, "메세지 불러오기 성공.", chatMessageListResponse), HttpStatus.OK);
	}



	@Transactional
	@MessageMapping("/chat/message/{chatRoomUniqueId}")
	public void message(@DestinationVariable("chatRoomUniqueId") String chatRoomUniqueId,ChatMessageRequest chatMessageRequest) {

		if(chatMessageRequest.getReadStatus() == null) {
			ChatRoom chatRoom = chatRoomRepository.findByisDeletedFalseAndChatRoomUniqueId(chatRoomUniqueId);
			List<PartyParticipate> partyParticipateList = partyParticipateRepository.findByisDeletedFalseAndAwaitingFalseAndChatRoomChatRoomUniqueId(chatRoomUniqueId);

			LocalDateTime createdAt = LocalDateTime.now();
			ChatMessageResponse chatMessageResponse = new ChatMessageResponse(chatMessageRequest, createdAt);
			ResponseDto responseDto = ResponseDto.setSuccess(200, "메세지 전송 성공", chatMessageResponse);
			ChatMessage chatMessage = new ChatMessage(chatMessageRequest, chatRoom, createdAt);
			chatMessageRepository.save(chatMessage);
			for (PartyParticipate partyParticipate : partyParticipateList) {

				if(!partyParticipate.getMember().getMemberUniqueId().equals(chatMessageRequest.getMemberUniqueId())){
					ChatCount chatCount = new ChatCount(partyParticipate, chatMessage);
					chatCountRepository.save(chatCount);
					List<ChatRoomListDto> chatRoomsList = getList(partyParticipate.getMember().getMemberUniqueId());
					ResponseDto responseRoomDto = ResponseDto.setSuccess(200, "채팅방 조회 성공", chatRoomsList);
					//sub주소로 메세지를 보내는
					messagingTemplate.convertAndSend("/sub/chat/chatList/" + partyParticipate.getMember().getMemberUniqueId(), responseRoomDto);
				}
			}
			messagingTemplate.convertAndSend("/sub/chat/message/" + chatRoomUniqueId, responseDto);
		}
		else{
			PartyParticipate partyParticipate = partyParticipateRepository.findByisDeletedFalseAndMemberMemberUniqueIdAndChatRoomChatRoomUniqueId(chatMessageRequest.getMemberUniqueId(), chatRoomUniqueId);
			List<ChatCount> chatCounts = chatCountRepository.findByisDeletedFalseAndReadStatusFalseAndPartyParticipate(partyParticipate);
			for (ChatCount chatCount : chatCounts) {
				chatCount.setReadStatus(true);
			}
		}
	}

	public List<ChatRoomListDto> getList(String memberUniqueId){

		List<PartyParticipate> partyParticipateList = partyParticipateRepository.findByisDeletedFalseAndMemberMemberUniqueIdAndAwaitingFalseOrderByChatRoomMessagesCreatedAtDesc(memberUniqueId, PageRequest.of(0, 10));
		List<ChatRoomListDto> chatRoomsList = new ArrayList<>();
		for(PartyParticipate partyParticipate : partyParticipateList){
			PartyParticipate participate = partyParticipateRepository.findByisDeletedFalseAndHostTrueAndParty(partyParticipate.getParty());
			long readNoneMessage = chatCountRepository.countByisDeletedFalseAndPartyParticipateAndReadStatusFalse(partyParticipate);
			List<String> imageList = partyParticipateRepository.findByJoinImage(partyParticipate.getParty());
			ChatRoomListDto chatRoomListDto = new ChatRoomListDto(partyParticipate, imageList, readNoneMessage, participate.getMember().getMemberUniqueId());
			chatRoomsList.add(chatRoomListDto);
		}
		return chatRoomsList;
	}

	// 로드밸런서 확인용
	@GetMapping("/test")
	public ResponseEntity<Void> forCheck() {
		return chatService.forCheck();
	}
}

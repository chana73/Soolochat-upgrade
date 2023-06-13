package com.soolo.soolochat.dto.response;

import java.time.LocalDateTime;

import com.soolo.soolochat.dto.request.ChatMessageListRequest;
import com.soolo.soolochat.entity.ChatMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatMessageList
 * @author        : mycom
 * @since         : 2023-05-28
 */
@Getter
@NoArgsConstructor
public class ChatMessageList {
	private String memberUniqueId;
	private String sender;
	private String memberProfileImage;
	private String message;
	private LocalDateTime createdAt;

	public ChatMessageList(ChatMessage chatMessage){
		this.memberUniqueId = chatMessage.getMemberUniqueId();
		this.sender = chatMessage.getSender();
		this.memberProfileImage = chatMessage.getMemberProfileImage();
		this.message = chatMessage.getMessage();
		this.createdAt = chatMessage.getCreatedAt();
	}
}

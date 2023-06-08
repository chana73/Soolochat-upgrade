package com.soolo.soolochat.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soolo.soolochat.dto.request.ChatMessageListRequest;
import com.soolo.soolochat.dto.request.ChatMessageRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatMessageResponse
 * @author        : mycom
 * @since         : 2023-05-28
 */
@Getter
@NoArgsConstructor
public class ChatMessageResponse {
	private Long memberId;
	private String message;
	private String sender;
	private String memberProfileImage;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	public ChatMessageResponse(ChatMessageRequest chatMessageRequest, LocalDateTime localDateTime){
		this.memberId = chatMessageRequest.getMemberId();
		this.message = chatMessageRequest.getMessage();
		this.sender = chatMessageRequest.getMemberName();
		this.memberProfileImage = chatMessageRequest.getMemberProfileImage();
		this.createdAt = localDateTime;
	}

}

package com.soolo.soolochat.dto.request;

import lombok.Getter;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatMessageList
 * @author        : mycom
 * @since         : 2023-05-28
 */
@Getter
public class ChatMessageListRequest {

	private Long chatRoomId;
	private String chatRoomUniqueId;
	private int page;

	public ChatMessageListRequest(Long chatRoomId, String chatRoomUniqueId, int page){
		this.chatRoomId = chatRoomId;
		this.chatRoomUniqueId = chatRoomUniqueId;
		this.page = page;

	}

}

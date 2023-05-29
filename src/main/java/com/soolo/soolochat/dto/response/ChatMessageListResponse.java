package com.soolo.soolochat.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatMessageListResponse
 * @author        : mycom
 * @since         : 2023-05-28
 */
@Getter
@NoArgsConstructor
public class ChatMessageListResponse {
	private List<ChatMessageList> chatMessageList;
	private int page;
	private int totalpage;

	public ChatMessageListResponse(List<ChatMessageList> chatMessageList, int page, int totalpage){
		this.chatMessageList = chatMessageList;
		this.page = page;
		this.totalpage = totalpage;
	}


}

package com.soolo.soolochat.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.soolo.soolochat.entity.ChatMessage;

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
@Setter
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

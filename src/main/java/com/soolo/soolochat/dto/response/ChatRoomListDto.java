package com.soolo.soolochat.dto.response;

import java.time.LocalDateTime;

import com.soolo.soolochat.connenct.PartyParticipate;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatRoomList
 * @author        : mycom
 * @since         : 2023-05-28
 */
@Getter
@NoArgsConstructor
public class ChatRoomListDto {
	private Long chatRoomId;
	private String chatRoomUniqueId;
	private String title;
	private int totalCount;
	private int currentCount;
	private String lastMessage;
	private LocalDateTime lastMessageTime;

	public ChatRoomListDto(PartyParticipate partyParticipate){
		this.chatRoomId = partyParticipate.getChatRoom().getChatRoomId();
		this.chatRoomUniqueId = partyParticipate.getChatRoom().getChatRoomUniqueId();
		this.title = partyParticipate.getChatRoom().getTitle();
		this.totalCount = partyParticipate.getParty().getTotalCount();
		this.currentCount = partyParticipate.getParty().getCurrentCount();
		this.lastMessage = partyParticipate.getChatRoom().getMessages().get(0).getMessage();
		this.lastMessageTime = partyParticipate.getChatRoom().getMessages().get(0).getCreatedAt();
	}

}

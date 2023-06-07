package com.soolo.soolochat.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
	private String imageUrl;
	private List<String> imageList;
	private int totalCount;
	private int currentCount;
	private long readCount;
	private String lastMessage;
	private LocalDateTime lastMessageTime;

	public ChatRoomListDto(PartyParticipate partyParticipate, List<String> imageList, Long readCount){
		this.chatRoomId = partyParticipate.getChatRoom().getChatRoomId();
		this.chatRoomUniqueId = partyParticipate.getChatRoom().getChatRoomUniqueId();
		this.title = partyParticipate.getChatRoom().getTitle();
		this.imageUrl = partyParticipate.getParty().getImageUrl();
		this.imageList = imageList;
		this.totalCount = partyParticipate.getParty().getTotalCount();
		this.currentCount = partyParticipate.getParty().getCurrentCount();
		this.readCount = readCount;
		this.lastMessage = partyParticipate.getChatRoom().getMessages().get(0).getMessage();
		this.lastMessageTime = partyParticipate.getChatRoom().getMessages().get(0).getCreatedAt();
	}

}

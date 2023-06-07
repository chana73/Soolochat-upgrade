package com.soolo.soolochat.dto.request;

import lombok.Getter;

/**
 * Please explain the class!!
 *
 * @fileName      : ChatMessage
 * @author        : mycom
 * @since         : 2023-05-28
 */
@Getter
public class ChatMessageRequest {

	private Long memberId;
	private String memberName;
	private String memberUniqueId;
	private String memberProfileImage;
	private Long chatRoomId;
	private String chatRoomUniqueId;
	private String message;
	private String readStatus;

}

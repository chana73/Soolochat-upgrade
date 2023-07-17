package com.soolo.soolochat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soolo.soolochat.dto.request.ChatMessageRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class ChatMessage extends Timestamped implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ChatMessageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private ChatRoom chatRoom;
    // 메시지 타입 : 입장, 채팅

    private Long memberId;
    private String memberUniqueId;
    private MessageType type; // 메시지 타입
    private String chatRoomUniqueId; // 방번호
    private String sender; // 메시지 보낸사람
    private String memberProfileImage; //프로필 이미지
    private String message; // 메// 시지
    private LocalDateTime messageCreatedAt;

    private boolean isDeleted = false;


    public enum MessageType {
        ENTER, EXIT
    }

    public ChatMessage(ChatMessageRequest chatMessageRequest, ChatRoom chatRoom, LocalDateTime localDateTime) {
        this.memberId = chatMessageRequest.getMemberId();
        this.memberUniqueId = chatMessageRequest.getMemberUniqueId();
        this.chatRoomUniqueId = chatMessageRequest.getChatRoomUniqueId();
        this.sender = chatMessageRequest.getMemberName();
        this.message = chatMessageRequest.getMessage();
        this.memberProfileImage = chatMessageRequest.getMemberProfileImage();
        this.messageCreatedAt = localDateTime;
        this.chatRoom = chatRoom;
    }


}

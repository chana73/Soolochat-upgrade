package com.soolo.soolochat.connenct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.soolo.soolochat.entity.ChatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PartyParticipate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Party party;

	@ManyToOne(fetch = FetchType.EAGER)
	private ChatRoom chatRoom;

	@Column(nullable = false)
	private boolean awaiting = true;

	@Column(nullable = false)
	private boolean rejected = false;

	@Column(nullable = false)
	private boolean host = false;



}

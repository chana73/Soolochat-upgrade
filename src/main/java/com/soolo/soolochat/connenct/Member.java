package com.soolo.soolochat.connenct;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.soolo.soolochat.entity.Timestamped;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name="Member")
public class Member extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(nullable = false)
	private String memberEmailId;

	@Column(nullable = false, unique = true)
	private String memberUniqueId;

	@Column(nullable = true)
	private int age;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private String memberName;

	private String introduce;

	private int point;

	private double latitude;

	private double longitude;

	private String profileImage;

	private String authority = "user";

	private String social;

	private LocalDateTime createdAt;


}

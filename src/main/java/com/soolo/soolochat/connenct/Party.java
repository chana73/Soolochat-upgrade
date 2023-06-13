package com.soolo.soolochat.connenct;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soolo.soolochat.entity.Timestamped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : Party
 * @author        : mycom
 * @since         : 2023-05-19
 */


@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Party extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long partyId;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;

	@Column
	private int totalCount;

	@ColumnDefault(value = "0")
	private int currentCount;

	@Column(nullable = false)

	// processing을 통해 모집 중 / 모집 마감 파티 리스트 활용  recruitmentStatus
	private boolean recruitmentStatus = true;

	private String concept;
	private String hostName;

	private Double latitude;
	private Double longitude;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modifiedAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime partyDate;

	@OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
	private List<PartyParticipate> partyParticipates = new ArrayList<>();


	private String regionName;
	private String placeName;
	private String placeAddress;
	private String placeUrl;

	private double distance;
	private String stationName;
	private double distanceFromMember;

	@Column(nullable = true)
	private String imageUrl;

	private boolean isDeleted = false;



}



package com.soolo.soolochat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soolo.soolochat.connenct.Member;
import com.soolo.soolochat.connenct.PartyParticipate;

/**
 * i
 */
public interface PartyParticipateRepository extends JpaRepository<PartyParticipate, Long> {

	@Query("select p from PartyParticipate p where p.chatRoom.chatRoomUniqueId = :chatRoomUniqueId and p.awaiting = false")
	List<PartyParticipate> findMemberBychatRoomUniqueId(@Param("chatRoomUniqueId") String chatRoomUniqueId);

	@Query("select p from PartyParticipate p where p.member.memberUniqueId = :memberUniqueId and p.chatRoom.chatRoomUniqueId = :chatRoomUniqueId")
	PartyParticipate findByMemberAndChatRoom(@Param("memberUniqueId") String memberUniqueId, @Param("chatRoomUniqueId") String chatRoomUniqueId);

}


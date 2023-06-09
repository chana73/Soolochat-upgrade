package com.soolo.soolochat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soolo.soolochat.connenct.Member;
import com.soolo.soolochat.connenct.Party;
import com.soolo.soolochat.connenct.PartyParticipate;

/**
 * i
 */
public interface PartyParticipateRepository extends JpaRepository<PartyParticipate, Long> {

	@Query("select p from PartyParticipate p where p.chatRoom.chatRoomUniqueId = :chatRoomUniqueId and p.awaiting = false")
	List<PartyParticipate> findMemberBychatRoomUniqueId(@Param("chatRoomUniqueId") String chatRoomUniqueId);

	@Query("select p from PartyParticipate p where p.member.memberUniqueId = :memberUniqueId and p.chatRoom.chatRoomUniqueId = :chatRoomUniqueId")
	PartyParticipate findByMemberAndChatRoom(@Param("memberUniqueId") String memberUniqueId, @Param("chatRoomUniqueId") String chatRoomUniqueId);

	@Query("select p.member.memberUniqueId from PartyParticipate p where p.party = :party and p.host = true")
	String findHost(@Param("party") Party party);

}


package com.soolo.soolochat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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


	/*@Query("SELECT p From PartyParticipate p join fetch p.member join fetch p.party join fetch p.chatRoom cr left join fetch cr.messages m " +
		"where p.member.memberUniqueId = :memberUniqueId and p.awaiting = false order by m.createdAt desc")
	List<PartyParticipate> findByMemberId(@Param("memberUniqueId") String memberUniqueId, Pageable pageable);*/
	@EntityGraph(attributePaths = {"member", "party", "chatRoom", "chatRoom.messages"})
	List<PartyParticipate> findByisDeletedFalseAndMemberMemberUniqueIdAndAwaitingFalseOrderByChatRoomMessagesCreatedAtDesc(String memberUniqueId, Pageable pageable);

	@Query("SELECT p.member.profileImage from PartyParticipate p where p.isDeleted = false and p.party = :party and p.awaiting = false order by p.host")
	List<String> findByJoinImage(@Param("party") Party party);

/*	@Query("select p from PartyParticipate p where p.chatRoom.chatRoomUniqueId = :chatRoomUniqueId and p.awaiting = false")
	List<PartyParticipate> findMemberBychatRoomUniqueId(@Param("chatRoomUniqueId") String chatRoomUniqueId);*/

	List<PartyParticipate> findByisDeletedFalseAndAwaitingFalseAndChatRoomChatRoomUniqueId(String chatRoomUniqueId);

/*	@Query("select p from PartyParticipate p where p.member.memberUniqueId = :memberUniqueId and p.chatRoom.chatRoomUniqueId = :chatRoomUniqueId")
	PartyParticipate findByMemberAndChatRoom(@Param("memberUniqueId") String memberUniqueId, @Param("chatRoomUniqueId") String chatRoomUniqueId);*/

	PartyParticipate findByisDeletedFalseAndMemberMemberUniqueIdAndChatRoomChatRoomUniqueId(String memberUniqueId, String chatRoomUniqueId);

/*	@Query("select p from PartyParticipate p where p.party = :party and p.host = true")
	PartyParticipate findHost(@Param("party") Party party);*/

	PartyParticipate findByisDeletedFalseAndHostTrueAndParty(Party party);

}


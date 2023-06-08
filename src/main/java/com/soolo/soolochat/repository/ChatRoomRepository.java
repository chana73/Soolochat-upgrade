package com.soolo.soolochat.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soolo.soolochat.connenct.Party;
import com.soolo.soolochat.connenct.PartyParticipate;
import com.soolo.soolochat.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findAll();

    @Query("SELECT cr from ChatRoom cr where cr.chatRoomUniqueId = :memberUniqueId")
    ChatRoom findByChatRoomId(@Param("memberUniqueId") String memberUniqueId);

    @Query("SELECT p From PartyParticipate p join fetch p.member join fetch p.party join fetch p.chatRoom cr left join fetch cr.messages m " +
        "where p.member.memberUniqueId = :memberUniqueId and p.awaiting = false order by m.createdAt desc")
    List<PartyParticipate> findByMemberId(@Param("memberUniqueId") String memberUniqueId, Pageable pageable);

    @Query("SELECT p.member.profileImage from PartyParticipate p where p.party = :party and p.awaiting = false order by p.host")
    List<String> findByJoinImage(@Param("party") Party party);


/*    @Query("SELECT CR FROM ChatRoom CR WHERE CR.roomId = :roomId")
    ChatRoom findRoomId(@Param("roomId") String roomId);*/

    //Optional<ChatRoom> findByRoomId(String RoomId);
    //void deleteByRoomId(String yourRoomId);

}

package com.soolo.soolochat.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.soolo.soolochat.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, Long> {

    List<ChatRoom> findAll();

/*    @Query("SELECT cr from ChatRoom cr where cr.chatRoomUniqueId = :memberUniqueId")
    ChatRoom findByChatRoomId(@Param("memberUniqueId") String memberUniqueId);*/

    ChatRoom findByisDeletedFalseAndChatRoomUniqueId(String memberUniqueId);

/*    @Query("SELECT CR FROM ChatRoom CR WHERE CR.roomId = :roomId")
    ChatRoom findRoomId(@Param("roomId") String roomId);*/

    //Optional<ChatRoom> findByRoomId(String RoomId);
    //void deleteByRoomId(String yourRoomId);

}

package com.example.naver.chat.repository;

import com.example.naver.chat.message.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByUser1EmailAndUser2Email(String user1Email, String user2Email);
}


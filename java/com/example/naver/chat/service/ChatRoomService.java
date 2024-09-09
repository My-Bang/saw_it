package com.example.naver.chat.service;

import com.example.naver.chat.message.ChatRoom;
import com.example.naver.chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public ChatRoom createOrFindChatRoom(String user1Email, String user2Email) {
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByUser1EmailAndUser2Email(user1Email, user2Email);

        if (existingRoom.isPresent()) {
            return existingRoom.get();
        }

        // 새로운 채팅방 생성
        ChatRoom newRoom = new ChatRoom();
        newRoom.setRoomId(UUID.randomUUID().toString());
        newRoom.setUser1Email(user1Email);
        newRoom.setUser2Email(user2Email);

        return chatRoomRepository.save(newRoom);
    }
}

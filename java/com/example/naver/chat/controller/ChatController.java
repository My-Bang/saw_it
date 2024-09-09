package com.example.naver.chat.controller;

import com.example.naver.chat.message.ChatMessage;
import com.example.naver.chat.repository.ChatMessageRepository;
import com.example.naver.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) {
        try {
            chatMessage.setRoomId(roomId);
            chatMessage.setTimestamp(LocalDateTime.now());

            // 메시지 전송 전에 서버 로그로 메시지 출력
            System.out.println("Received message: " + chatMessage.getContent());

            // 데이터베이스에 메시지 저장
            chatMessageRepository.save(chatMessage);

            return chatMessage;  // 채팅 메시지를 해당 채팅방으로 브로드캐스트
        } catch (Exception e) {
            System.err.println("Error while sending message: " + e.getMessage());
            return null; // 또는 적절한 응답
        }
    }

    @GetMapping("/chat/messages/{roomId}")
    @ResponseBody
    public List<ChatMessage> getMessages(@PathVariable String roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderEmail());
        return chatMessage;
    }
}

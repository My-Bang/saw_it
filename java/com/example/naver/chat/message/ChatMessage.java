package com.example.naver.chat.message;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class ChatMessage {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "room_id")
    private String roomId; // 추가된 부분
    private String senderEmail;
    private String receiverEmail; // 수신자 이메일 추가
    private String content;
    private LocalDateTime timestamp;
    // 추가: roomId getter


    public ChatMessage() {}

    public ChatMessage(String senderEmail, String receiverEmail, String content, LocalDateTime timestamp, String roomId) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.timestamp = timestamp;
        this.roomId = roomId; // 추가: 생성자에서 roomId 초기화
    }


}

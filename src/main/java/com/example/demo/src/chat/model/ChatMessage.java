package com.example.demo.src.chat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {   // 메세지 보낼 때 사용

    // 메시지 타입 : 입장, 채팅
    enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}
package com.example.demo.src.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.src.chat.model.ChatMessage;
import com.example.demo.src.chat.model.ChatRoom;
import com.example.demo.src.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    // ObjectMapper : JSON을 Java 객체로 변환할 수 있고, 반대로 Java 객체를 JSON 객체로 serialization 할 수 있음
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // payload : 전송되는 데이터
        log.info("payload {}", payload);

        // 1) web socket test
        // TextMessage textMessage = new TextMessage("Welcome chatting sever~^^");
        // session.sendMessage(textMessage);

        // 2) 실제 채팅 로직
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }
}

    
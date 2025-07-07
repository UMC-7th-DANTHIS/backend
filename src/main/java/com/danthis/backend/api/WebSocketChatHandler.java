package com.danthis.backend.api;

import com.danthis.backend.application.auth.implement.TokenProvider;
import com.danthis.backend.application.chat.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler implements WebSocketHandler {

  private final ObjectMapper objectMapper;
  private final TokenProvider tokenProvider;
  private final ChatService chatService;

  // 모든 세션
  private final Set<WebSocketSession> sessions = new HashSet<>();

  // 방별 세션
  private final Map<Long, Set<WebSocketSession>> chatRoomSessions = new HashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("✅ 연결됨: {}", session.getId());
    sessions.add(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("💬 받은 메시지 payload: {}", payload);

    // JSON -> DTO 변환
    ChatDTO.Message chatMessage = objectMapper.readValue(payload, ChatDTO.Message.class);
    log.info("💬 파싱된 ChatMessage: {}", chatMessage);

    // 인증 추출 (JWT 토큰)
    String authorization = session.getHandshakeHeaders().getFirst("Authorization");
    Long userId = tokenProvider.getUserIdFromToken(authorization.replace("Bearer ", ""));
    chatMessage = chatMessage.toBuilder().senderId(userId).build();

    handleChatMessage(session, chatMessage);
  }

  private void handleChatMessage(WebSocketSession session, ChatDTO.Message chatMessage) {
    Long roomId = chatMessage.getChatRoomId();

    chatRoomSessions.putIfAbsent(roomId, new HashSet<>());
    Set<WebSocketSession> roomSessions = chatRoomSessions.get(roomId);
    roomSessions.add(session);

    // 메시지 타입 분기
    switch (chatMessage.getType()) {
      case ENTER -> {
        chatMessage = chatMessage.toBuilder()
                                 .message(chatMessage.getSenderName() + "님이 입장했습니다.")
                                 .build();
      }
      case OUT -> {
        chatMessage = chatMessage.toBuilder()
                                 .message(chatMessage.getSenderName() + "님이 퇴장했습니다.")
                                 .build();
        roomSessions.remove(session);
      }
      case TALK -> {
        // 저장
        chatService.saveMessage(chatMessage);
      }
    }

    // 같은 방의 모든 세션에 전달
    sendMessageToRoom(roomId, chatMessage);
  }

  private void sendMessageToRoom(Long roomId, ChatDTO.Message message) {
    try {
      String json = objectMapper.writeValueAsString(message);
      for (WebSocketSession ws : chatRoomSessions.get(roomId)) {
        if (ws.isOpen()) {
          ws.sendMessage(new TextMessage(json));
        }
      }
    } catch (Exception e) {
      log.error("💥 메시지 전송 실패", e);
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("❎ 연결 끊김: {}", session.getId());
    sessions.remove(session);

    // 모든 방에서 제거
    for (Set<WebSocketSession> room : chatRoomSessions.values()) {
      room.remove(session);
    }
  }
}

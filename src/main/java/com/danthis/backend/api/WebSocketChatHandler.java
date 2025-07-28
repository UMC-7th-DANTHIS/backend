package com.danthis.backend.api;

import com.danthis.backend.application.auth.implement.TokenProvider;
import com.danthis.backend.application.chat.ChatMessageService;
import com.danthis.backend.application.chat.implement.ChatMessageReader;
import com.danthis.backend.application.chat.request.ChatMessageDTO;
import com.danthis.backend.application.chat.response.ChatMessageResponseDTO;
import com.danthis.backend.domain.user.User;
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
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;
  private final TokenProvider tokenProvider;
  private final ChatMessageService chatMessageService;
  private final ChatMessageReader chatMessageReader;

  /**
   * 채팅방별 세션 목록
   */
  private final Map<Long, Set<WebSocketSession>> chatRoomSessions = new HashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    log.info(" WebSocket 연결됨: {}", session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    ChatMessageDTO incoming = objectMapper.readValue(message.getPayload(), ChatMessageDTO.class);

    // JWT에서 userId 추출
    String authorization = session.getHandshakeHeaders().getFirst("Authorization");
    Long userId = tokenProvider.getUserIdFromToken(authorization.replace("Bearer ", ""));
    User sender = chatMessageReader.readUserById(userId);

    handleChatMessage(session, incoming, sender);
  }

  private void handleChatMessage(WebSocketSession session, ChatMessageDTO message, User sender) {
    Long roomId = message.getChatRoomId();

    switch (message.getType()) {
      case ENTER -> {
        log.info(" ENTER: userId={}, opponentId={}", sender.getId(), message.getOpponentId());

        // 채팅방 없으면 생성
        if (roomId == null) {
          roomId = chatMessageService.ensureChatRoomExists(sender.getId(), message.getOpponentId());
        }

        chatRoomSessions.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);

        // 시스템 입장 메시지
        sendSystemMessage(roomId, sender.getNickname() + "님이 입장했습니다.");
      }

      case TALK -> {
        log.info(" TALK: roomId={}, sender={}", roomId, sender.getNickname());

        ChatMessageResponseDTO talkMsg =
            chatMessageService.saveMessage(sender.getId(), roomId, message.getMessage());

        sendMessageToRoom(roomId, talkMsg);
      }

      case OUT -> {
        log.info(" OUT: roomId={}, sender={}", roomId, sender.getNickname());

        if (roomId != null) {
          chatRoomSessions.getOrDefault(roomId, new HashSet<>()).remove(session);
          sendSystemMessage(roomId, sender.getNickname() + "님이 퇴장했습니다.");
        }
      }
    }
  }

  /**
   * 일반 메시지 전송
   */
  private void sendMessageToRoom(Long roomId, Object message) {
    try {
      String json = objectMapper.writeValueAsString(message);
      Set<WebSocketSession> roomSessions = chatRoomSessions.get(roomId);
      if (roomSessions != null) {
        for (WebSocketSession ws : roomSessions) {
          if (ws.isOpen()) {
            ws.sendMessage(new TextMessage(json));
          }
        }
      }
    } catch (Exception e) {
      log.error(" 메시지 전송 실패", e);
    }
  }

  /**
   * 입장/퇴장 같은 시스템 메시지 전송
   */
  private void sendSystemMessage(Long roomId, String content) {
    ChatMessageDTO systemMsg = ChatMessageDTO.builder()
                                             .type(ChatMessageDTO.MessageType.TALK) // 시스템 알림도 TALK 형식으로 전달
                                             .chatRoomId(roomId)
                                             .message(content)
                                             .build();

    sendMessageToRoom(roomId, systemMsg);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    log.info("❎ 연결 종료: {}", session.getId());

    // 세션이 들어있는 방에서 제거
    chatRoomSessions.values().forEach(room -> room.remove(session));
  }
}

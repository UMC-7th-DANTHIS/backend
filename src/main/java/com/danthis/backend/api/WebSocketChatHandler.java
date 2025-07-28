package com.danthis.backend.api;

import com.danthis.backend.application.auth.implement.TokenProvider;
import com.danthis.backend.application.chat.ChatMessageService;
import com.danthis.backend.application.chat.request.ChatMessageDTO;
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

  private final Set<WebSocketSession> sessions = new HashSet<>();
  private final Map<Long, Set<WebSocketSession>> chatRoomSessions = new HashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    log.info("✅ WebSocket 연결됨: {}", session.getId());
    sessions.add(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("💬 받은 메시지: {}", payload);

    ChatMessageDTO incoming = objectMapper.readValue(payload, ChatMessageDTO.class);

    String authorization = session.getHandshakeHeaders().getFirst("Authorization");
    Long userId = tokenProvider.getUserIdFromToken(authorization.replace("Bearer ", ""));
    User sender = chatMessageService.getUserById(userId);

    handleChatMessage(session, incoming, sender);
  }

  private void handleChatMessage(WebSocketSession session, ChatMessageDTO message, User sender) {
    Long roomId = message.getChatRoomId();

    switch (message.getType()) {
      case ENTER -> {
        log.info("✅ ENTER 요청 - userId={}, dancerId={}", sender.getId(), message.getOpponentId());

        if (roomId == null) {
          roomId = chatMessageService.ensureChatRoomExists(sender.getId(), message.getOpponentId());
        }

        chatRoomSessions.putIfAbsent(roomId, new HashSet<>());
        chatRoomSessions.get(roomId).add(session);

        ChatMessageDTO enterMsg = ChatMessageDTO.builder()
                                                .type(ChatMessageDTO.MessageType.TALK)
                                                .chatRoomId(roomId)
                                                .message(sender.getNickname() + "님이 입장했습니다.")
                                                .build();

        sendMessageToRoom(roomId, enterMsg);
      }

      case TALK -> {
        log.info("✅ TALK 메시지 저장");
        chatMessageService.saveMessage(sender, roomId, message.getMessage());

        ChatMessageDTO talkMsg = ChatMessageDTO.builder()
                                               .type(ChatMessageDTO.MessageType.TALK)
                                               .chatRoomId(roomId)
                                               .message(message.getMessage())
                                               .build();

        sendMessageToRoom(roomId, talkMsg);
      }

      case OUT -> {
        log.info("✅ OUT 처리");
        if (roomId != null) {
          chatRoomSessions.getOrDefault(roomId, new HashSet<>()).remove(session);
        }

        ChatMessageDTO outMsg = ChatMessageDTO.builder()
                                              .type(ChatMessageDTO.MessageType.TALK)
                                              .chatRoomId(roomId)
                                              .message(sender.getNickname() + "님이 퇴장했습니다.")
                                              .build();

        sendMessageToRoom(roomId, outMsg);
      }
    }
  }

  private void sendMessageToRoom(Long roomId, ChatMessageDTO message) {
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
      log.error("💥 메시지 전송 실패", e);
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    log.info("❎ 연결 종료: {}", session.getId());
    sessions.remove(session);
    for (Set<WebSocketSession> room : chatRoomSessions.values()) {
      room.remove(session);
    }
  }
}

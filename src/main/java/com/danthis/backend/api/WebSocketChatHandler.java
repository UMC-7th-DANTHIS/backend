package com.danthis.backend.api;

import com.danthis.backend.application.chat.ChatMessageService;
import com.danthis.backend.application.chat.implement.ChatMessageReader;
import com.danthis.backend.application.chat.request.ChatMessageDTO;
import com.danthis.backend.application.chat.response.ChatMessageResponseDTO;
import com.danthis.backend.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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

    Long userId = (Long) session.getAttributes().get("userId");
    if (userId == null) {
      throw new IllegalStateException("WebSocket userId not found (handshake not authenticated).");
    }

    User sender = chatMessageReader.readUserById(userId);
    handleChatMessage(session, incoming, sender);
  }

  private void handleChatMessage(WebSocketSession session, ChatMessageDTO message, User sender) {
    Long roomId = message.getChatRoomId();

    switch (message.getType()) {
      case ENTER -> {
        log.info(" ENTER: userId={}, opponentId={}", sender.getId(), message.getOpponentId());

        // 1) 방 없으면 생성
        if (roomId == null) {
          if (message.getOpponentId() == null) {
            sendToSession(session, error("ENTER requires opponentId"));
            return;
          }
          roomId = chatMessageService.ensureChatRoomExists(sender.getId(), message.getOpponentId());
        }

        // 2) 세션 방에 등록
        chatRoomSessions.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);

        Long opponentId = (message.getOpponentId() != null)
            ? message.getOpponentId()
            : chatMessageService.resolveOpponentId(roomId, sender.getId());

        ChatMessageResponseDTO enterAck = ChatMessageResponseDTO.builder()
                                                              .type(ChatMessageDTO.MessageType.ENTER)
                                                              .chatRoomId(roomId)
                                                              .opponentId(opponentId)
                                                              .senderId(sender.getId())
                                                              .senderNickname(sender.getNickname())
                                                              .message("ENTER_OK")
                                                              .sentAt(LocalDateTime.now())
                                                              .system(true)
                                                              .build();

        sendToSession(session, enterAck);

        sendSystemMessage(roomId, opponentId, sender.getNickname() + "님이 입장했습니다.");
      }

      case TALK -> {
        if (roomId == null) {
          sendToSession(session, error("TALK requires chatRoomId"));
          return;
        }

        Long opponentId = (message.getOpponentId() != null)
            ? message.getOpponentId()
            : chatMessageService.resolveOpponentId(roomId, sender.getId());

        log.info(" TALK: roomId={}, sender={}", roomId, sender.getNickname());

        ChatMessageResponseDTO saved =
            chatMessageService.saveMessage(sender.getId(), roomId, message.getMessage());

        ChatMessageResponseDTO talk = ChatMessageResponseDTO.builder()
                                                          .type(ChatMessageDTO.MessageType.TALK)
                                                          .chatRoomId(saved.getChatRoomId())
                                                          .opponentId(opponentId)
                                                          .messageId(saved.getMessageId())
                                                          .senderId(saved.getSenderId())
                                                          .senderNickname(saved.getSenderNickname())
                                                          .message(saved.getMessage())
                                                          .sentAt(saved.getSentAt())
                                                          .system(false)
                                                          .build();

        sendMessageToRoom(roomId, talk);
      }

      case OUT -> {
        if (roomId == null) {
          sendToSession(session, error("OUT requires chatRoomId"));
          return;
        }

        Long opponentId = (message.getOpponentId() != null)
            ? message.getOpponentId()
            : chatMessageService.resolveOpponentId(roomId, sender.getId());

        log.info(" OUT: roomId={}, sender={}", roomId, sender.getNickname());

        chatRoomSessions.getOrDefault(roomId, new HashSet<>()).remove(session);

        ChatMessageResponseDTO outEvent = ChatMessageResponseDTO.builder()
                                                              .type(ChatMessageDTO.MessageType.OUT)
                                                              .chatRoomId(roomId)
                                                              .opponentId(opponentId)
                                                              .senderId(sender.getId())
                                                              .senderNickname(sender.getNickname())
                                                              .message("OUT_OK")
                                                              .sentAt(LocalDateTime.now())
                                                              .system(true)
                                                              .build();

        sendMessageToRoom(roomId, outEvent);

        sendSystemMessage(roomId, opponentId, sender.getNickname() + "님이 퇴장했습니다.");
      }
    }
  }

  private void sendToSession(WebSocketSession session, Object payload) {
    try {
      if (!session.isOpen()) return;
      String json = objectMapper.writeValueAsString(payload);
      session.sendMessage(new TextMessage(json));
    } catch (Exception e) {
      log.error(" 세션 메시지 전송 실패", e);
    }
  }

  private void sendMessageToRoom(Long roomId, Object payload) {
    try {
      String json = objectMapper.writeValueAsString(payload);
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

  private void sendSystemMessage(Long roomId, Long opponentId, String content) {
    ChatMessageResponseDTO system = ChatMessageResponseDTO.builder()
                                                        .type(ChatMessageDTO.MessageType.TALK)
                                                        .chatRoomId(roomId)
                                                        .opponentId(opponentId)
                                                        .message(content)
                                                        .sentAt(LocalDateTime.now())
                                                        .system(true)
                                                        .build();

    sendMessageToRoom(roomId, system);
  }

  private ChatMessageResponseDTO error(String msg) {
    return ChatMessageResponseDTO.builder()
                                .type(ChatMessageDTO.MessageType.TALK)
                                .message("[ERROR] " + msg)
                                .sentAt(LocalDateTime.now())
                                .system(true)
                                .build();
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    log.info(" 연결 종료: {}", session.getId());
    chatRoomSessions.values().forEach(room -> room.remove(session));
  }
}

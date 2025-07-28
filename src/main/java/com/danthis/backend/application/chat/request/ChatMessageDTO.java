package com.danthis.backend.application.chat.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {

  public enum MessageType {
    ENTER, TALK, OUT
  }

  private MessageType type;
  private Long chatRoomId;
  private Long opponentId; // dancerId
  private String message;
}
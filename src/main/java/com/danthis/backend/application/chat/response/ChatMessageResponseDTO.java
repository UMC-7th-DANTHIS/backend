package com.danthis.backend.application.chat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseDTO {

  private Long senderId;
  private String senderNickname;
  private String message;
  private String sentAt;
}

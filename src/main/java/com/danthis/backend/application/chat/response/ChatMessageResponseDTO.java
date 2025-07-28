package com.danthis.backend.application.chat.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseDTO {

  private Long chatRoomId;
  private String senderNickname;
  private String message;
  private LocalDateTime sentAt;
}

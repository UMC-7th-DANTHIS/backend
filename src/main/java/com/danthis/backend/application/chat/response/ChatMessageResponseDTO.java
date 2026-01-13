package com.danthis.backend.application.chat.response;

import com.danthis.backend.application.chat.request.ChatMessageDTO;
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

  private ChatMessageDTO.MessageType type;

  private Long chatRoomId;
  private Long opponentId;

  private Long senderId;
  private String senderNickname;
  private Long messageId;
  private String message;
  private LocalDateTime sentAt;

  private boolean system;
}

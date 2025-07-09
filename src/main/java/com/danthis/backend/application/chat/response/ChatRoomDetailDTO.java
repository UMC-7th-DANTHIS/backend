package com.danthis.backend.application.chat.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailDTO {

  private Long chatRoomId;
  private Long opponentId;
  private String opponentNickname;
  private String opponentProfileImage;
  private List<ChatMessageResponseDTO> messages;
}

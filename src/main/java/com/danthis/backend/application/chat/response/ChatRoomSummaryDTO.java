package com.danthis.backend.application.chat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomSummaryDTO {

  private Long chatRoomId;
  private Long opponentId;
  private String opponentNickname;
  private String opponentProfileImage;
}

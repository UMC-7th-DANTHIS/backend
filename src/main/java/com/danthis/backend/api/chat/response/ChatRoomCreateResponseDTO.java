package com.danthis.backend.api.chat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateResponseDTO {

  private Long chatRoomId;
  private Long dancerId;
  private String dancerNickname;
  private String dancerProfileImage;
}

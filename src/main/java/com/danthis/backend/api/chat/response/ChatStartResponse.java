package com.danthis.backend.api.chat.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatStartResponse {

  private Long dancerId;
  private String dancerName;
  private String openChatUrl;
}

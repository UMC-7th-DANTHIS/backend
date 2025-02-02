package com.danthis.backend.application.chat.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserChatListServiceResponse {

  private Long userId;
  private List<ChatDancerSummary> chatList;

  @Getter
  @Builder
  public static class ChatDancerSummary {

    private Long chatId;
    private Long dancerId;
    private String dancerName;
    private String profileImage;
  }
}

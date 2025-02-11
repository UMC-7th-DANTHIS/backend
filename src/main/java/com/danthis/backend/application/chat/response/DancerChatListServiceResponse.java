package com.danthis.backend.application.chat.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerChatListServiceResponse {

  private Long dancerId;
  private int currentPage;
  private int totalPages;
  private int totalUsers;
  private List<ChatUserSummary> chatUsers;

  @Getter
  @Builder
  public static class ChatUserSummary {

    private Long userId;
    private String nickname;
    private String profileImage;
  }
}

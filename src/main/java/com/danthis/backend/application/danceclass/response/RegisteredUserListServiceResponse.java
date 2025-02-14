package com.danthis.backend.application.danceclass.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisteredUserListServiceResponse {

  private Long classId;
  private String className;
  private String classImage;
  private int currentPage;
  private int totalPages;
  private int totalUsers;
  private List<UserSummary> users;

  @Getter
  @Builder
  public static class UserSummary {

    private Long userId;
    private String nickname;
    private String profileImage;
  }
}

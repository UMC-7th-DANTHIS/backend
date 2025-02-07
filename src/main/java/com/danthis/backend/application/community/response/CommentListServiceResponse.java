package com.danthis.backend.application.community.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentListServiceResponse {

  private Long postId;
  private int currentPage;
  private int totalPages;
  private int totalComments;
  private List<CommentSummary> comments;

  @Getter
  @Builder
  public static class CommentSummary {

    private Long commentId;
    private String userName;
    private String userProfileImage;
    private LocalDateTime createdAt;
    private String content;
  }
}

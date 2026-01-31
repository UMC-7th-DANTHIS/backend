package com.danthis.backend.application.community.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyCommentListServiceResponse {

  private Long userId;
  private int currentPage;
  private int totalPages;
  private long totalComments;
  private List<MyCommentInfo> comments;

  @Getter
  @Builder
  public static class MyCommentInfo {

    private Long postId;
    private Long commentId;
    private String content;
    private String createAt;
  }
}

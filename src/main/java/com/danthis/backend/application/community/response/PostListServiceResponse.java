package com.danthis.backend.application.community.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListServiceResponse {

  private int currentPage;
  private int totalPages;
  private int totalPosts;
  private List<PostSummary> posts;

  @Getter
  @Builder
  public static class PostSummary {

    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private int commentCount;
  }
}

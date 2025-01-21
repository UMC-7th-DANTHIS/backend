package com.danthis.backend.application.user.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCommentsResponse {

  private List<CommentDto> posts;
  private PaginationDto pagination;

  @Getter
  @Builder
  public static class CommentDto {

    private Long id;
    private Long postId;
    private String content;
  }

  @Getter
  @Builder
  public static class PaginationDto {

    private Integer currentPage;
    private Integer totalPages;
  }
}

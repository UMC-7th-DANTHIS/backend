package com.danthis.backend.application.user.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCommunityResponse {

  @Getter
  @Builder
  public static class UserCommentsResponse {

    private List<CommentDto> comments;
    private PaginationDto pagination;
  }

  @Getter
  @Builder
  public static class UserPostsResponse {

    private List<PostDto> posts;
    private PaginationDto pagination;
  }

  @Getter
  @Builder
  public static class CommentDto {

    private Long id;
    private Long postId;
    private String content;
  }

  @Getter
  @Builder
  public static class PostDto {

    private Long id;
    private String title;
    private String content;
    private List<String> images;
  }

  @Getter
  @Builder
  public static class PaginationDto {

    private Integer currentPage;
    private Integer totalPages;
  }
}

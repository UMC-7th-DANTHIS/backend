package com.danthis.backend.application.user.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPostsResponse {

  List<PostDto> posts;
  Pagination pagination;

  public static UserPostsResponse from(final List<PostDto> posts, final Pagination pagination) {
    return UserPostsResponse.builder()
                            .posts(posts)
                            .pagination(pagination)
                            .build();
  }

  @Getter
  @Builder
  public static class PostDto {

    private Long postId;
    private String title;
    private String content;
    private List<String> images;
  }

  @Getter
  @Builder
  public static class Pagination {

    private Integer currentPage;
    private Integer totalPages;
  }
}

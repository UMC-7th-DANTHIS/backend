package com.danthis.backend.application.user.response;

import com.danthis.backend.domain.communitypost.CommunityPost;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class UserPostsResponse {

  private List<PostDto> posts;
  private Integer currentPage;
  private Integer totalPages;
  private Long totalElements;

  public static UserPostsResponse from(final List<PostDto> posts, Integer currentPage, Integer totalPages, Long totalElements) {
    return UserPostsResponse.builder()
                            .posts(posts)
                            .currentPage(currentPage + 1)
                            .totalPages(totalPages)
                            .totalElements(totalElements)
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

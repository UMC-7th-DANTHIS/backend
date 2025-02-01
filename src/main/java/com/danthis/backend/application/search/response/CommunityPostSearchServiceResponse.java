package com.danthis.backend.application.search.response;

import com.danthis.backend.domain.communitypost.CommunityPost;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class CommunityPostSearchServiceResponse {

  private String query;
  private List<PostSummary> results;
  private Pagination pagination;

  @Getter
  @Builder
  public static class PostSummary {

    private Long id;
    private String title;
    private String content;
    private List<String> postImages;
  }

  @Getter
  @Builder
  public static class Pagination {

    private int currentPage;
    private int totalPages;
    private long totalResults;
  }

  public static CommunityPostSearchServiceResponse from(String query,
      Page<CommunityPost> postPage) {
    List<PostSummary> postSummaries = postPage.getContent().stream()
                                              .map(post -> PostSummary.builder()
                                                                      .id(post.getId())
                                                                      .title(post.getTitle())
                                                                      .content(truncateContent(post.getContent()))
                                                                      .postImages(post.getCommunityPostImages()
                                                                                      .stream()
                                                                                      .map(image -> image.getUrl())
                                                                                      .toList())
                                                                      .build())
                                              .toList();

    Pagination paginationInfo = Pagination.builder()
                                          .currentPage(postPage.getNumber() + 1)
                                          .totalPages(postPage.getTotalPages())
                                          .totalResults(postPage.getTotalElements())
                                          .build();

    return CommunityPostSearchServiceResponse.builder()
                                             .query(query)
                                             .results(postSummaries)
                                             .pagination(paginationInfo)
                                             .build();
  }

  private static String truncateContent(String content) {
    int maxLength = 210;
    if (content.length() <= maxLength) {
      return content;
    }
    return content.substring(0, maxLength) + " ...";
  }
}

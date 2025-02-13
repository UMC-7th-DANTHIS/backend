package com.danthis.backend.application.user.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReviewResponse {

  private List<ReviewDto> reviews;
  private Integer currentPage;
  private Integer totalPages;
  private Long totalElements;

  public static UserReviewResponse from(List<ReviewDto> reviews, Integer currentPage, Integer totalPages, Long totalElements) {
    return UserReviewResponse.builder()
                             .reviews(reviews)
                             .currentPage(currentPage + 1)
                             .totalPages(totalPages)
                             .totalElements(totalElements)
                             .build();
  }

  @Getter
  @Builder
  public static class ReviewDto {

    private Long reviewId;
    private Long classId;
    private String title;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
    private Set<String> images;
  }
}

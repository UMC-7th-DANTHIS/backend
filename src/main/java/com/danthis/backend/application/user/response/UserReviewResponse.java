package com.danthis.backend.application.user.response;

import com.danthis.backend.application.user.response.UserPostsResponse.Pagination;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReviewResponse {

  List<ReviewDto> reviews;
  Pagination pagination;

  public static UserReviewResponse from(List<ReviewDto> reviews, Pagination pagination) {
    return UserReviewResponse.builder()
                             .reviews(reviews)
                             .pagination(pagination)
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

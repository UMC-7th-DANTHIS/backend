package com.danthis.backend.application.review.response;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewReadServiceResponse {

  private Long reviewId;
  private Long classId;
  private String title;
  private String content;
  private String author;
  private LocalDateTime createdAt;
  private Set<String> images;
}

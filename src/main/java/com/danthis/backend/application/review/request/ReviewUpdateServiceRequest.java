package com.danthis.backend.application.review.request;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewUpdateServiceRequest {

  private Long reviewId;
  private String title;
  private String content;
  private Integer rating;
  private Set<String> reviewImages;
}

package com.danthis.backend.application.review.implement;

import com.danthis.backend.application.review.response.ReviewReadServiceResponse;
import com.danthis.backend.domain.classreview.ClassReview;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

  public ReviewReadServiceResponse toReviewResponse(ClassReview review) {
    return ReviewReadServiceResponse.builder()
                                    .reviewId(review.getId())
                                    .classId(review.getDanceClass().getId())
                                    .title(review.getTitle())
                                    .content(review.getContent())
                                    .author(review.getUser().getNickname())
                                    .createdAt(review.getCreatedAt())
                                    .images(mapReviewImages(review))
                                    .build();
  }

  private Set<String> mapReviewImages(ClassReview review) {
    return review.getClassReviewImages().stream()
                 .map(image -> image.getImageUrl())
                 .collect(Collectors.toSet());
  }
}

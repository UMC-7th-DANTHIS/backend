package com.danthis.backend.application.review.implement;

import com.danthis.backend.application.user.response.UserReviewResponse.ReviewDto;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.classreview.classreviewimage.ClassReviewImage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewManager {

  public List<ReviewDto> toReviewDtoList(List<ClassReview> reviews) {
    return reviews.stream()
                  .map(review -> ReviewDto.builder()
                                          .reviewId(review.getId())
                                          .classId(review.getDanceClass().getId())
                                          .title(review.getTitle())
                                          .content(review.getContent())
                                          .rating(review.getRating())
                                          .createdAt(review.getCreatedAt())
                                          .images(review.getClassReviewImages()
                                                        .stream()
                                                        .map(ClassReviewImage::getImageUrl)
                                                        .collect(Collectors.toSet()))
                                          .build())
                  .toList();
  }
}

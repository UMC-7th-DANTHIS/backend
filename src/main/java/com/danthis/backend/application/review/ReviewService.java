package com.danthis.backend.application.review;

import com.danthis.backend.application.review.implement.ReviewManager;
import com.danthis.backend.application.review.implement.ReviewMapper;
import com.danthis.backend.application.review.implement.ReviewReader;
import com.danthis.backend.application.review.request.ReviewCreateServiceRequest;
import com.danthis.backend.application.review.response.ReviewReadServiceResponse;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewManager reviewManager;
  private final ReviewReader reviewReader;
  private final ReviewMapper reviewMapper;

  @Transactional
  public void createReview(Long classId, Long userId, ReviewCreateServiceRequest request) {
    DanceClass danceClass = reviewManager.getDanceClassById(classId);
    User user = reviewManager.getUserById(userId);

    reviewManager.createClassReview(danceClass, user, request);
  }

  @Transactional
  public ReviewReadServiceResponse getReview(Long classId, Long reviewId) {
    ClassReview review = reviewReader.readReviewByIdAndClassId(reviewId, classId);
    return reviewMapper.toReviewResponse(review);
  }
}

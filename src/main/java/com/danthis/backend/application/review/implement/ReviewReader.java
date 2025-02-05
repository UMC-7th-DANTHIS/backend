package com.danthis.backend.application.review.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.classreview.repository.ClassReviewRepository;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReader {

  private final ClassReviewRepository classReviewRepository;
  private final DanceClassRepository danceClassRepository;

  public ClassReview readReviewByIdAndClassId(Long reviewId, Long classId) {
    validateDanceClassExists(classId);
    return classReviewRepository.findByIdAndDanceClassId(reviewId, classId)
                                .orElseThrow(
                                    () -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
  }

  public Page<ClassReview> readReviewsByClassId(Long classId, Pageable pageable) {
    validateDanceClassExists(classId);
    return classReviewRepository.findByDanceClassId(classId, pageable);
  }

  public Double calculateAverageRatingByDanceClassId(Long classId) {
    validateDanceClassExists(classId);
    return classReviewRepository.calculateAverageRatingByDanceClassId(classId);
  }

  public Long countReviewsByDanceClassId(Long classId) {
    validateDanceClassExists(classId);
    return classReviewRepository.countByDanceClassId(classId);
  }

  private void validateDanceClassExists(Long classId) {
    if (!danceClassRepository.existsById(classId)) {
      throw new BusinessException(ErrorCode.DANCE_CLASS_NOT_FOUND);
    }
  }

  public Page<ClassReview> readReviewsByUserId(Long classId, Pageable pageable) {
    return classReviewRepository.findByUserId(classId, pageable);
  }
}

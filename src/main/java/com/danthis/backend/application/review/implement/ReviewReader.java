package com.danthis.backend.application.review.implement;

import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.classreview.repository.ClassReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReader {

  private final ClassReviewRepository classReviewRepository;

  public Page<ClassReview> readReviewsByUserId(Long classId, Pageable pageable) {
    return classReviewRepository.findByUserId(classId, pageable);
  }
}

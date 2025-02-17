package com.danthis.backend.application.review.implement.mapping;

import com.danthis.backend.domain.classreview.classreviewimage.repository.ClassReviewImageRepository;
import com.danthis.backend.domain.danceclass.DanceClass;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewImageManager {

  private final ClassReviewImageRepository classReviewImageRepository;

  @Transactional
  public void deleteReviewImagesByDanceClass(DanceClass danceClass) {
    classReviewImageRepository.deleteByDanceClassId(danceClass.getId());
  }
}

package com.danthis.backend.domain.classreview.classreviewimage.repository;

public interface ClassReviewImageRepositoryCustom {

  void deleteByDanceClassId(Long classId);

  void deleteByReviewId(Long reviewId);
}

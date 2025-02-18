package com.danthis.backend.domain.classreview.classreviewimage.repository;

import com.danthis.backend.domain.classreview.classreviewimage.QClassReviewImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClassReviewImageRepositoryImpl implements ClassReviewImageRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public void deleteByDanceClassId(Long classId) {
    jpaQueryFactory.delete(QClassReviewImage.classReviewImage)
                   .where(QClassReviewImage.classReviewImage.classReview.danceClass.id.eq(classId)
                       .and(QClassReviewImage.classReviewImage.isActive.eq(true)))
                   .execute();
  }

  @Override
  public void deleteByReviewId(Long reviewId) {
    jpaQueryFactory.delete(QClassReviewImage.classReviewImage)
                   .where(QClassReviewImage.classReviewImage.classReview.id.eq(reviewId)
                       .and(QClassReviewImage.classReviewImage.isActive.eq(true)))
                   .execute();
  }
}

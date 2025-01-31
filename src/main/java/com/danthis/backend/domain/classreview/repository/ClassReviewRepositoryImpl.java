package com.danthis.backend.domain.classreview.repository;

import static com.danthis.backend.domain.classreview.QClassReview.classReview;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClassReviewRepositoryImpl implements ClassReviewRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Double calculateAverageRatingByDanceClassId(Long classId) {
    return jpaQueryFactory.select(classReview.rating.avg())
                          .from(classReview)
                          .where(classReview.danceClass.id.eq(classId)
                                                          .and(classReview.isActive.eq(true)))
                          .fetchOne();
  }
}

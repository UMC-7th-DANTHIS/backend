package com.danthis.backend.domain.classreview.repository;

import static com.danthis.backend.domain.classreview.QClassReview.classReview;

import com.danthis.backend.domain.classreview.ClassReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

  @Override
  public Page<ClassReview> findByUserId(Long userId, Pageable pageable) {
    List<ClassReview> reviews = jpaQueryFactory.selectFrom(classReview)
                                               .where(classReview.user.id.eq(userId).and(
                                                   classReview.isActive.eq(true)))
                                               .offset(pageable.getOffset())
                                               .limit(pageable.getPageSize())
                                               .fetch();

    long totalElements = Optional.ofNullable(jpaQueryFactory
                                     .select(classReview.count())
                                     .from(classReview)
                                     .where(classReview.user.id.eq(userId)
                                                               .and(classReview.isActive.eq(true)))
                                     .fetchOne())
                                 .orElse(0L);

    return new PageImpl<>(reviews, pageable, totalElements);
  }
}

package com.danthis.backend.domain.danceclass.repository;

import static com.danthis.backend.domain.danceclass.QDanceClass.danceClass;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DanceClassRepositoryImpl implements DanceClassRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<DanceClass> searchByClassName(String query, Pageable pageable) {
    BooleanExpression searchCondition = danceClass.className.containsIgnoreCase(query)
                                                            .and(danceClass.isActive.eq(true));

    List<DanceClass> results = jpaQueryFactory.selectFrom(danceClass)
                                              .where(searchCondition)
                                              .offset(pageable.getOffset())
                                              .limit(pageable.getPageSize())
                                              .fetch();

    long total = jpaQueryFactory.select(danceClass.count())
                                .from(danceClass)
                                .where(searchCondition)
                                .fetchOne();

    return new PageImpl<>(results, pageable, total);
  }
}

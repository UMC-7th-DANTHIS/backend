package com.danthis.backend.domain.dancer.repository;

import static com.danthis.backend.domain.dancer.QDancer.dancer;

import com.danthis.backend.domain.dancer.Dancer;
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
public class DancerRepositoryImpl implements DancerRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Dancer> searchByDancerName(String query, Pageable pageable) {
    BooleanExpression searchCondition = dancer.dancerName.containsIgnoreCase(query)
                                                         .and(dancer.isActive.eq(true));

    List<Dancer> results = jpaQueryFactory.selectFrom(dancer)
                                          .where(searchCondition)
                                          .offset(pageable.getOffset())
                                          .limit(pageable.getPageSize())
                                          .fetch();

    long total = jpaQueryFactory.select(dancer.count())
                                .from(dancer)
                                .where(searchCondition)
                                .fetchOne();

    return new PageImpl<>(results, pageable, total);
  }
}

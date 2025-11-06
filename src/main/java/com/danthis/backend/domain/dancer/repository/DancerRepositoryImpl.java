package com.danthis.backend.domain.dancer.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.QDancer;
import com.danthis.backend.domain.genre.QGenre;
import com.danthis.backend.domain.mapping.dancergenre.QDancerGenre;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DancerRepositoryImpl implements DancerRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QDancer dancer = QDancer.dancer;
  QDancerGenre dancerGenre = QDancerGenre.dancerGenre;
  QGenre genre = QGenre.genre;

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

  @Override
  public List<Dancer> findByGenres(Set<Long> genreList) {
    return jpaQueryFactory.selectDistinct(dancer)
                          .from(dancer)
                          .join(dancer.dancerGenres, dancerGenre)
                          .join(dancerGenre.genre, genre)
                          .where(genre.id.in(genreList))
                          .fetch();
  }
}

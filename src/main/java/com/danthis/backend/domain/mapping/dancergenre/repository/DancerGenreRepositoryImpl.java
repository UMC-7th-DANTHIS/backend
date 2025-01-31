package com.danthis.backend.domain.mapping.dancergenre.repository;

import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.dancergenre.QDancerGenre;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DancerGenreRepositoryImpl implements DancerGenreRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QDancerGenre dancerGenre = QDancerGenre.dancerGenre;

  @Override
  public void deleteByDancer(Long dancerId) {
    jpaQueryFactory.delete(dancerGenre)
                   .where(dancerGenre.dancer.id.eq(dancerId).and(dancerGenre.isActive.eq(true)))
                   .execute();
  }

  @Override
  public List<DancerGenre> findByDancer(Long dancerId) {
    return jpaQueryFactory.selectFrom(dancerGenre)
                          .where(dancerGenre.dancer.id.eq(dancerId)
                                                      .and(dancerGenre.isActive.eq(true)))
                          .fetch();
  }

  @Override
  public Page<DancerGenre> findByGenreId(Long genreId, Pageable pageable) {
    List<DancerGenre> dancerGenres = jpaQueryFactory.selectFrom(dancerGenre)
                                                    .where(dancerGenre.genre.id.eq(genreId)
                                                                               .and(
                                                                                   dancerGenre.isActive.eq(
                                                                                       true)))
                                                    .offset(pageable.getOffset())
                                                    .limit(pageable.getPageSize())
                                                    .fetch();

    long totalElements = jpaQueryFactory.selectFrom(dancerGenre)
                                        .where(dancerGenre.genre.id.eq(genreId)
                                                                   .and(dancerGenre.isActive.eq(
                                                                       true)))
                                        .fetchCount();

    return new PageImpl<>(dancerGenres, pageable, totalElements);
  }
}

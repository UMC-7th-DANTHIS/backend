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
  public Page<DancerGenre> findByGenreId(Long genreId, Pageable pageable) {
    List<DancerGenre> dancerGenres = jpaQueryFactory.selectFrom(dancerGenre)
                                                    .where(dancerGenre.genre.id.eq(genreId))
                                                    .offset(pageable.getOffset())
                                                    .limit(pageable.getPageSize())
                                                    .fetch();

    Long totalElements = jpaQueryFactory.selectFrom(dancerGenre)
                                        .where(dancerGenre.genre.id.eq(genreId))
                                        .fetchCount();

    return new PageImpl<>(dancerGenres, pageable, totalElements);
  }
}

package com.danthis.backend.domain.mapping.usergenre.repository;

import com.danthis.backend.domain.mapping.usergenre.QUserGenre;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserGenreRepositoryImpl implements UserGenreRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QUserGenre userGenre = QUserGenre.userGenre;

  @Override
  public void deleteByUser(Long userId) {
    jpaQueryFactory.delete(userGenre)
                   .where(userGenre.user.id.eq(userId)
                                           .and(userGenre.isActive.eq(true)))
                   .execute();
  }

  @Override
  public List<UserGenre> findByUser(Long userId) {
    return jpaQueryFactory.selectFrom(userGenre)
                          .where(userGenre.user.id.eq(userId)
                                                  .and(userGenre.isActive.eq(true)))
                          .fetch();
  }
}

package com.danthis.backend.domain.mapping.danceclasshashtag.repository;

import static com.danthis.backend.domain.mapping.danceclasshashtag.QDanceClassHashtag.danceClassHashtag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DanceClassHashtagRepositoryImpl implements DanceClassHashtagRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public void deleteByDanceClassId(Long classId) {
    jpaQueryFactory.delete(danceClassHashtag)
                   .where(danceClassHashtag.danceClass.id.eq(classId)
                                                         .and(danceClassHashtag.isActive.eq(true)))
                   .execute();
  }
}

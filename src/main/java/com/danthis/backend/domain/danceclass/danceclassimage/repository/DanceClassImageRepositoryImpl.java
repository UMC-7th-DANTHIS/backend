package com.danthis.backend.domain.danceclass.danceclassimage.repository;

import static com.danthis.backend.domain.danceclass.danceclassimage.QDanceClassImage.danceClassImage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DanceClassImageRepositoryImpl implements DanceClassImageRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public void deleteByDanceClassId(Long classId) {
    jpaQueryFactory.delete(danceClassImage)
                   .where(danceClassImage.danceClass.id.eq(classId)
                                                       .and(danceClassImage.isActive.eq(true)))
                   .execute();
  }
}

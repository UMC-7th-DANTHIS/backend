package com.danthis.backend.domain.dancer.dancerimage.repository;

import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.dancerimage.QDancerImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DancerImageRepositoryImpl implements DancerImageRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QDancerImage dancerImage = QDancerImage.dancerImage;

  @Override
  public void deleteByDancer(Long dancerId) {
    jpaQueryFactory.delete(dancerImage)
                   .where(dancerImage.dancer.id.eq(dancerId).and(dancerImage.isActive.eq(true)))
                   .execute();
  }

  @Override
  public List<DancerImage> findByDancer(Long dancerId) {
    return jpaQueryFactory.selectFrom(dancerImage)
                          .where(dancerImage.dancer.id.eq(dancerId)
                                                      .and(dancerImage.isActive.eq(true)))
                          .fetch();
  }
}

package com.danthis.backend.domain.practiceroom.repository;

import com.danthis.backend.domain.practiceroom.PracticeRoom;
import com.danthis.backend.domain.practiceroom.QPracticeRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PracticeRoomRepositoryImpl implements PracticeRoomRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QPracticeRoom qPracticeRoom = QPracticeRoom.practiceRoom;

  @Override
  public List<PracticeRoom> findAllByLocation(Double longitude, Double latitude, Double radius) {
    return jpaQueryFactory.selectFrom(qPracticeRoom)
                          .where(
                              qPracticeRoom.latitude.between(latitude - radius, latitude + radius),
                              qPracticeRoom.longitude.between(longitude - radius,
                                  longitude + radius)
                          ).fetch();
  }
}

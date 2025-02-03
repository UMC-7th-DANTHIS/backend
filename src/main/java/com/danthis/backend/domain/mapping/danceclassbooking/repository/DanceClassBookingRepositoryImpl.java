package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import static com.danthis.backend.domain.mapping.danceclassbooking.QDanceClassBooking.danceClassBooking;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DanceClassBookingRepositoryImpl implements DanceClassBookingRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<DanceClassBooking> findApprovedBookingsByClass(DanceClass danceClass) {
    return queryFactory
        .selectFrom(danceClassBooking)
        .where(
            danceClassBooking.danceClass.eq(danceClass)
                                        .and(danceClassBooking.isActive.isTrue())
                                        .and(danceClassBooking.isApproved.isTrue())
        )
        .fetch();
  }
}

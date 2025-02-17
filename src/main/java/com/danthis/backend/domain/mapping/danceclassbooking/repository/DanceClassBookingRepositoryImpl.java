package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import static com.danthis.backend.domain.mapping.danceclassbooking.QDanceClassBooking.danceClassBooking;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.danceclassbooking.QDanceClassBooking;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DanceClassBookingRepositoryImpl implements DanceClassBookingRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<DanceClassBooking> findApprovedBookingsByClass(DanceClass danceClass,
      Pageable pageable) {
    List<DanceClassBooking> results = jpaQueryFactory
        .selectFrom(danceClassBooking)
        .where(
            danceClassBooking.danceClass.eq(danceClass)
                                        .and(danceClassBooking.isActive.eq(true))
                                        .and(danceClassBooking.isApproved.eq(true))
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    long total = jpaQueryFactory
        .select(danceClassBooking.count())
        .from(danceClassBooking)
        .where(
            danceClassBooking.danceClass.eq(danceClass)
                                        .and(danceClassBooking.isActive.eq(true))
                                        .and(danceClassBooking.isApproved.eq(true))
        )
        .fetchOne();

    return new PageImpl<>(results, pageable, total);
  }

  @Override
  public void deleteByDanceClass(DanceClass danceClass) {
    jpaQueryFactory.delete(QDanceClassBooking.danceClassBooking)
                   .where(QDanceClassBooking.danceClassBooking.danceClass.eq(danceClass)
                                                                         .and(
                                                                             danceClassBooking.isActive.eq(true)))
                   .execute();
  }
}

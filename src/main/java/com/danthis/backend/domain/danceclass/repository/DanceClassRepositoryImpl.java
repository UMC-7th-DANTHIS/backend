package com.danthis.backend.domain.danceclass.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.QDanceClass;
import com.danthis.backend.domain.danceclass.danceclassschedule.QDanceClassSchedule;
import com.danthis.backend.domain.danceclass.danceclassschedule.Week;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DanceClassRepositoryImpl implements DanceClassRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QDanceClass danceClass = QDanceClass.danceClass;
  private final QDanceClassSchedule danceClassSchedule = QDanceClassSchedule.danceClassSchedule;

  @Override
  public Page<DanceClass> findByGenreIdAndDate(Long genreId, LocalDate date, PageRequest pageable) {

    List<DanceClass> danceClasses = jpaQueryFactory
        .selectFrom(danceClass)
        .distinct()
        .join(danceClass.danceClassSchedules, danceClassSchedule)
        .where(
            danceClass.isActive.eq(true),
            danceClass.genre.id.eq(genreId),
            danceClassSchedule.date.eq(date)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return new PageImpl<>(danceClasses, pageable, danceClasses.size());
  }

  @Override
  public Page<DanceClass> findByGenreIdAndDay(Long genreId, Week day, PageRequest pageable) {

    List<DanceClass> danceClasses = jpaQueryFactory
        .selectFrom(danceClass)
        .distinct()
        .join(danceClass.danceClassSchedules, danceClassSchedule)
        .where(
            danceClass.isActive.eq(true),
            danceClass.genre.id.eq(genreId),
            danceClassSchedule.day.eq(day)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return new PageImpl<>(danceClasses, pageable, danceClasses.size());
  }

  @Override
  public List<DanceClass> findByGenreIds(Set<Long> genreIds) {
    return jpaQueryFactory.selectFrom(danceClass)
                          .where(danceClass.genre.id.in(genreIds))
                          .fetch();
  }
}

package com.danthis.backend.domain.danceclass.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.QDanceClass;
import com.danthis.backend.domain.danceclass.danceclassschedule.QDanceClassSchedule;
import com.danthis.backend.domain.danceclass.danceclassschedule.Week;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    long total = Optional.ofNullable(jpaQueryFactory
        .select(danceClass.count()) // join 시 중복이 발생하므로 countDistinct 사용
        .distinct()
        .from(danceClass)
        .join(danceClass.danceClassSchedules, danceClassSchedule)
        .where(
            danceClass.isActive.eq(true),
            danceClass.genre.id.eq(genreId),
            danceClassSchedule.date.eq(date)
        )
        .fetchOne()).orElse(0L);

    return new PageImpl<>(danceClasses, pageable, total);
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

    long total = Optional.ofNullable(jpaQueryFactory
        .select(danceClass.count())
        .distinct()
        .from(danceClass)
        .where(
            danceClass.isActive.eq(true),
            danceClass.genre.id.eq(genreId),
            danceClassSchedule.day.eq(day)
        )
        .fetchOne()).orElse(0L);

    return new PageImpl<>(danceClasses, pageable, total);
  }

  @Override
  public List<DanceClass> findByGenreIds(Set<Long> genreIds) {
    return jpaQueryFactory.selectFrom(danceClass)
                          .where(danceClass.genre.id.in(genreIds))
                          .fetch();
  }

  @Override
  public Page<DanceClass> findByGenreIdAndDateOrDay(Long genreId, LocalDate date, Week day,
      PageRequest pageable) {

    List<DanceClass> danceClasses = jpaQueryFactory
        .selectFrom(danceClass)
        .distinct()
        .join(danceClass.danceClassSchedules, danceClassSchedule)
        .where(
            danceClass.isActive.eq(true),
            danceClass.genre.id.eq(genreId),
            danceClassSchedule.date.eq(date).or(danceClassSchedule.day.eq(day))
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(danceClass.createdAt.desc()) // 최신순 정렬
        .fetch();

    long total = Optional.ofNullable(jpaQueryFactory
        .select(danceClass.count()) // join 시 중복이 발생하므로 countDistinct 사용
        .distinct()
        .from(danceClass)
        .join(danceClass.danceClassSchedules, danceClassSchedule)
        .where(
            danceClass.isActive.eq(true),
            danceClass.genre.id.eq(genreId),
            danceClassSchedule.date.eq(date).or(danceClassSchedule.day.eq(day))
        )
        .fetchOne()).orElse(0L);

    return new PageImpl<>(danceClasses, pageable, total);
  }
}

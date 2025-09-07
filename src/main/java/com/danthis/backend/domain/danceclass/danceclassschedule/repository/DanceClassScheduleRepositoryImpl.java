package com.danthis.backend.domain.danceclass.danceclassschedule.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DanceClassScheduleRepositoryImpl implements DanceClassScheduleRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

}

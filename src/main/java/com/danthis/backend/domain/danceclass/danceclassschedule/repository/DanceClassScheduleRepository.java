package com.danthis.backend.domain.danceclass.danceclassschedule.repository;

import com.danthis.backend.domain.danceclass.danceclassschedule.DanceClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassScheduleRepository extends JpaRepository<DanceClassSchedule, Long>,
    DanceClassScheduleRepositoryCustom {

}

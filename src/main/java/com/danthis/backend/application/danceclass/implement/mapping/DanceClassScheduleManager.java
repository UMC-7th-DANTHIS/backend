package com.danthis.backend.application.danceclass.implement.mapping;

import com.danthis.backend.application.danceclass.implement.DanceClassMapper;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassschedule.DanceClassSchedule;
import com.danthis.backend.domain.danceclass.danceclassschedule.repository.DanceClassScheduleRepository;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassScheduleManager {

  private final DanceClassScheduleRepository danceClassScheduleRepository;
  private final DanceClassMapper danceClassMapper;

  @Transactional
  public void deleteSchedulesByDanceClass(DanceClass danceClass) {
    danceClassScheduleRepository.deleteByDanceClassId(danceClass.getId());
  }

  @Transactional
  public void updateSchedule(DanceClass danceClass, Set<String> days, Set<String> dates) {
    danceClassScheduleRepository.deleteByDanceClassId(danceClass.getId());

    if (days != null) {
      Set<DanceClassSchedule> newSchedules = danceClassMapper.mapToDays(danceClass, days);
      danceClassScheduleRepository.saveAll(newSchedules);

    }
    if (dates != null) {
      Set<DanceClassSchedule> newSchedules = danceClassMapper.mapToDates(danceClass, dates);
      danceClassScheduleRepository.saveAll(newSchedules);
    }
  }
}

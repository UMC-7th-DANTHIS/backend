package com.danthis.backend.application.danceclass.implement.mapping;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassschedule.repository.DanceClassScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassScheduleManager {

  private final DanceClassScheduleRepository danceClassScheduleRepository;

  @Transactional
  public void deleteSchedulesByDanceClass(DanceClass danceClass) {
    danceClassScheduleRepository.deleteByDanceClassId(danceClass.getId());
  }

}

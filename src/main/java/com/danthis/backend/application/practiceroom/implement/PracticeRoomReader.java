package com.danthis.backend.application.practiceroom.implement;

import com.danthis.backend.domain.practiceroom.PracticeRoom;
import com.danthis.backend.domain.practiceroom.repository.PracticeRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PracticeRoomReader {

  private final PracticeRoomRepository practiceRoomRepository;

  public List<PracticeRoom> getRooms(Double longitude, Double latitude, Double radius) {
    return practiceRoomRepository.findAllByLocation(
        longitude, latitude, radius);
  }
}

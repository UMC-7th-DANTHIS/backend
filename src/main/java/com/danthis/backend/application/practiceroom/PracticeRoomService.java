package com.danthis.backend.application.practiceroom;

import com.danthis.backend.application.practiceroom.implement.PracticeRoomReader;
import com.danthis.backend.application.practiceroom.response.PracticeRoomListResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.practiceroom.PracticeRoom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PracticeRoomService {

  private final PracticeRoomReader practiceRoomReader;

  public PracticeRoomListResponse getPracticeRoomsByLocation(Double longitude, Double latitude,
      Double radius) {

    if (radius < 0) {
      throw new BusinessException(ErrorCode.INVALID_NEGATIVE_NUMBER);
    }
    List<PracticeRoom> practiceRooms = practiceRoomReader.getRooms(longitude, latitude, radius);
    return PracticeRoomListResponse.of(practiceRooms);
  }

  public PracticeRoomListResponse getAllPracticeRoom() {
    List<PracticeRoom> practiceRooms = practiceRoomReader.getAllRooms();
    return PracticeRoomListResponse.of(practiceRooms);
  }
}

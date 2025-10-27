package com.danthis.backend.domain.practiceroom.repository;

import com.danthis.backend.domain.practiceroom.PracticeRoom;
import java.util.List;

public interface PracticeRoomRepositoryCustom {

  List<PracticeRoom> findAllByLocation(Double longitude, Double latitude, Double radius);
}

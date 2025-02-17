package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import com.danthis.backend.domain.danceclass.DanceClass;

public interface DanceClassBookingRepositoryCustom {

  void deleteByDanceClass(DanceClass danceClass);
}

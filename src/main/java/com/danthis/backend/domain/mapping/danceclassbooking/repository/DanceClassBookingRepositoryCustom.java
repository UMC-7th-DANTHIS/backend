package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DanceClassBookingRepositoryCustom {

  Page<DanceClassBooking> findApprovedBookingsByClass(DanceClass danceClass, Pageable pageable);

  void deleteByDanceClass(DanceClass danceClass);
}

package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import java.util.List;

public interface DanceClassBookingRepositoryCustom {

  List<DanceClassBooking> findApprovedBookingsByClass(DanceClass danceClass);
}

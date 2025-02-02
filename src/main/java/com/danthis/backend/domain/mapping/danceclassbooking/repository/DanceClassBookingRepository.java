package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import com.danthis.backend.domain.danceclass.repository.DanceClassRepositoryCustom;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassBookingRepository extends JpaRepository<DanceClassBooking, Long>,
    DanceClassRepositoryCustom {

  List<DanceClassBooking> findByDanceClassDancer(Dancer dancer);
}

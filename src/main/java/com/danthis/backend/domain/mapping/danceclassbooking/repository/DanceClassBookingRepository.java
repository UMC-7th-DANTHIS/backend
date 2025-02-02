package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassBookingRepository extends JpaRepository<DanceClassBooking, Long>,
    DanceClassBookingRepositoryCustom {

  List<DanceClassBooking> findByDanceClassDancer(Dancer dancer);

  List<DanceClassBooking> findByUser(User user);

  Optional<DanceClassBooking> findByDanceClassAndUser(DanceClass danceClass, User user);
}

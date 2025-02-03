package com.danthis.backend.domain.mapping.danceclassbooking.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.user.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassBookingRepository extends JpaRepository<DanceClassBooking, Long>,
    DanceClassBookingRepositoryCustom {

  Page<DanceClassBooking> findByDanceClassDancer(Dancer dancer, Pageable pageable);

  Page<DanceClassBooking> findByUser(User user, Pageable pageable);

  Optional<DanceClassBooking> findByDanceClassAndUser(DanceClass danceClass, User user);
}

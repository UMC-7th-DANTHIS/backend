package com.danthis.backend.application.chat.implement;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.danceclassbooking.repository.DanceClassBookingRepository;
import com.danthis.backend.domain.user.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatManager {

  private final DanceClassBookingRepository danceClassBookingRepository;

  public DanceClassBooking createBooking(User user, DanceClass danceClass, Dancer dancer) {
    DanceClassBooking booking = DanceClassBooking.builder()
                                                 .user(user)
                                                 .danceClass(danceClass)
                                                 .bookingDate(LocalDateTime.now())
                                                 .isApproved(false)
                                                 .build();

    return danceClassBookingRepository.save(booking);
  }
}

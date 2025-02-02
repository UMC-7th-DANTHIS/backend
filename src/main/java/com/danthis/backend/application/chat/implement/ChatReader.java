package com.danthis.backend.application.chat.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.danceclassbooking.repository.DanceClassBookingRepository;
import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatReader {

  private final UserRepository userRepository;
  private final DanceClassRepository danceClassRepository;
  private final DancerRepository dancerRepository;
  private final DanceClassBookingRepository bookingRepository;

  public User readUserById(Long userId) {
    return userRepository.findById(userId)
                         .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }

  public DanceClass readDanceClassById(Long classId) {
    return danceClassRepository.findById(classId).orElseThrow(
        () -> new BusinessException(ErrorCode.DANCE_CLASS_NOT_FOUND));
  }

  public boolean isDancer(Long dancerId) {
    return dancerRepository.existsById(dancerId);
  }

  public List<DanceClassBooking> readBookingsByDancer(Dancer dancer) {
    return bookingRepository.findByDanceClassDancer(dancer);
  }

  public boolean isUser(Long userId) {
    return userRepository.existsById(userId);
  }

  public List<DanceClassBooking> readBookingsByUser(User user) {
    return bookingRepository.findByUser(user);
  }
}

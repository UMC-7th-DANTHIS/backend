package com.danthis.backend.application.chat.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.danceclassbooking.repository.DanceClassBookingRepository;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.mapping.danceruserchat.repository.DancerUserChatRepository;
import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatReader {

  private final UserRepository userRepository;
  private final DanceClassRepository danceClassRepository;
  private final DancerRepository dancerRepository;
  private final DanceClassBookingRepository bookingRepository;
  private final DancerUserChatRepository dancerUserChatRepository;

  public User readUserById(Long userId) {
    return userRepository.findById(userId)
                         .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }

  public Dancer readDancerById(Long dancerId) {
    return dancerRepository.findById(dancerId)
                           .orElseThrow(() -> new BusinessException(ErrorCode.DANCER_NOT_FOUND));
  }

  public boolean isUserDancer(Long userId) {
    User user = userRepository.findById(userId)
                              .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    return dancerRepository.existsByUser(user);
  }

  public Page<DancerUserChat> readChatsByDancer(Dancer dancer, int page, int size) {
    return dancerUserChatRepository.findByDancer(dancer, PageRequest.of(page - 1, size));
  }


  //
  public DanceClass readDanceClassById(Long classId) {
    return danceClassRepository.findById(classId).orElseThrow(
        () -> new BusinessException(ErrorCode.DANCE_CLASS_NOT_FOUND));
  }

  public boolean isUser(Long userId) {
    return userRepository.existsById(userId);
  }

  public Page<DanceClassBooking> readBookingsByDancer(Dancer dancer, int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    return bookingRepository.findByDanceClassDancer(dancer, pageable);
  }

  public Page<DanceClassBooking> readBookingsByUser(User user, int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    return bookingRepository.findByUser(user, pageable);
  }
}

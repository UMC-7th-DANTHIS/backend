package com.danthis.backend.application.chat.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.mapping.danceruserchat.repository.DancerUserChatRepository;
import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatReader {

  private final UserRepository userRepository;
  private final DancerRepository dancerRepository;
  private final DancerUserChatRepository dancerUserChatRepository;

  public User readUserById(Long userId) {
    return userRepository.findById(userId)
                         .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }

  public Dancer readDancerById(Long dancerId) {
    return dancerRepository.findById(dancerId)
                           .orElseThrow(() -> new BusinessException(ErrorCode.DANCER_NOT_FOUND));
  }

  public Page<DancerUserChat> readChatsByDancer(Dancer dancer, int page, int size) {
    return dancerUserChatRepository.findByDancer(dancer, PageRequest.of(page - 1, size));
  }

  public Page<DancerUserChat> readChatsByUser(User user, int page, int size) {
    return dancerUserChatRepository.findByUser(user, PageRequest.of(page - 1, size));
  }
}

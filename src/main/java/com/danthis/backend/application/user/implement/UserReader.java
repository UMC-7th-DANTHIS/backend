package com.danthis.backend.application.user.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

  private final UserRepository userRepository;

  public User readUserById(Long userId) {
    return userRepository.findById(userId)
                         .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }
}

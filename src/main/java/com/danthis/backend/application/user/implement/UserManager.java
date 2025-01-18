package com.danthis.backend.application.user.implement;

import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

  private final UserRepository userRepository;

  public void withdrawUser(User user) {
    user.deactivate();
    userRepository.save(user);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
}

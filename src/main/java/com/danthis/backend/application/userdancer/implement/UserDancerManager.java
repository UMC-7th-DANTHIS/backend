package com.danthis.backend.application.userdancer.implement;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.userdancer.repository.UserDancerRepository;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDancerManager {

  private final UserDancerRepository userDancerRepository;

  public UserDancer createFromIds(User user, Dancer dancer) {
    return UserDancer.builder()
                     .user(user)
                     .dancer(dancer)
                     .build();
  }

  public void saveUserDancer(UserDancer userDancer) {
    userDancerRepository.save(userDancer);
  }

  public void deleteUserDancer(UserDancer userDancer) {
    userDancerRepository.delete(userDancer);
  }
}

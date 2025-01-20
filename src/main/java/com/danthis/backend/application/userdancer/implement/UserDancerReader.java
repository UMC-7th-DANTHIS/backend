package com.danthis.backend.application.userdancer.implement;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.userdancer.repository.UserDancerRepository;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDancerReader {

  private final UserDancerRepository userDancerRepository;

  public UserDancer readUserDancerByUserIdAndDancerId(User user, Dancer dancer) {
    return userDancerRepository.findUserDancerByUserIdAndDancerId(user, dancer);
  }
}

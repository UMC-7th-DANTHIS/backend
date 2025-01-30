package com.danthis.backend.application.user.implement;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.userdancer.repository.UserDancerRepository;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDancerManager {

  private final UserDancerRepository userDancerRepository;

  @Transactional
  public void saveAll(Set<UserDancer> userDancers) {
    userDancerRepository.saveAll(userDancers);
  }

  @Transactional
  public void deleteByUser(User user) {
    userDancerRepository.deleteByUser(user);
  }
}

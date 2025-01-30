package com.danthis.backend.application.user.implement;

import com.danthis.backend.domain.mapping.userdancer.repository.UserDancerRepository;
import com.danthis.backend.domain.user.User;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDancerReader {

  private final UserDancerRepository userDancerRepository;

  public Set<Long> findDancerIdsByUser(User user) {
    return userDancerRepository.findByUser(user).stream()
                               .map(userDancer -> userDancer.getDancer().getId())
                               .collect(Collectors.toSet());
  }
}

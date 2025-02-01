package com.danthis.backend.application.user.implement.mapping;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.userdancer.repository.UserDancerRepository;
import com.danthis.backend.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDancerReader {

  private final UserDancerRepository userDancerRepository;

  public List<Long> findDancerIdsByUser(User user) {
    return userDancerRepository.findByUser(user.getId())
                               .stream()
                               .map(userDancer -> userDancer.getDancer().getId())
                               .toList();
  }

  public Page<UserDancer> readDancersByUserId(Long userId, Pageable pageable) {
    return userDancerRepository.findByUserId(userId, pageable);
  }
}

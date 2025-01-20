package com.danthis.backend.application.userdancer;

import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.application.userdancer.implement.UserDancerManager;
import com.danthis.backend.application.userdancer.implement.UserDancerReader;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDancerService {

  public final UserReader userReader;
  public final DancerReader dancerReader;
  public final UserDancerManager userDancerManager;
  public final UserDancerReader userDancerReader;

  @Transactional
  public void addFavoriteDancer(Long userId, Long dancerId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    UserDancer userDancer = userDancerManager.createFromIds(user, dancer);
    userDancerManager.saveUserDancer(userDancer);
  }

  @Transactional
  public void removeFavoriteDancer(Long userId, Long dancerId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    UserDancer userDancer = userDancerReader.readUserDancerByUserIdAndDancerId(user, dancer);
    userDancerManager.deleteUserDancer(userDancer);
  }
}

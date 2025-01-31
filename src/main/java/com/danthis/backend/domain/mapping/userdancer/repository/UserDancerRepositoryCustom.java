package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import java.util.List;
import com.danthis.backend.domain.user.User;

public interface UserDancerRepositoryCustom {

  void deleteByUser(Long userId);

  List<UserDancer> findByUser(Long userId);

  UserDancer findUserDancerByUserAndDancer(User user, Dancer dancer);
}

package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.user.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDancerRepositoryCustom {

  UserDancer findUserDancerByUserAndDancer(User user, Dancer dancer);

  void deleteByUser(Long userId);

  List<UserDancer> findByUser(Long userId);

  Page<UserDancer> findByUserId(Long userId, Pageable pageable);
}

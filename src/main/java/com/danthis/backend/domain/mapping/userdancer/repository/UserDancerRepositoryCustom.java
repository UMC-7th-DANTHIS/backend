package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import java.util.List;

public interface UserDancerRepositoryCustom {

  void deleteByUser(Long userId);

  List<UserDancer> findByUser(Long userId);
}

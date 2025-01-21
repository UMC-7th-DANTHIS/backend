package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.user.User;

public interface UserDancerRepositoryCustom {

  UserDancer findUserDancerByUserAndDancer(User user, Dancer dancer);
}

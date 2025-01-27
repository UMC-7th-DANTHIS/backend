package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDancerRepositoryCustom {

  Page<UserDancer> findByUserId(Long userId, Pageable pageable);
}

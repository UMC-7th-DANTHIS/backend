package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDancerRepositoryCustom {

  void deleteByUser(Long userId);

  List<UserDancer> findByUser(Long userId);

  Page<UserDancer> findByUserId(Long userId, Pageable pageable);
}

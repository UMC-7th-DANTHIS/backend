package com.danthis.backend.domain.user.repository;

import com.danthis.backend.domain.user.User;
import java.util.Optional;

public interface UserRepositoryCustom {

  Optional<User> findByEmail(String email);

  boolean existsByNickname(String nickname);

  boolean existsByEmail(String email);
}

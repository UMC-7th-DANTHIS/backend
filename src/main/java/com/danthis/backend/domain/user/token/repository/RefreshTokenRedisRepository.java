package com.danthis.backend.domain.user.token.repository;

import com.danthis.backend.domain.user.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {
}

package com.danthis.backend.domain.user.token.repository;

import com.danthis.backend.domain.user.token.BlacklistToken;
import org.springframework.data.repository.CrudRepository;

public interface BlacklistTokenRedisRepository extends CrudRepository<BlacklistToken, String> {
}

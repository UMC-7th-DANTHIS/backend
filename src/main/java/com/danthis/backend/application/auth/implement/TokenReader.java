package com.danthis.backend.application.auth.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.user.token.RefreshToken;
import com.danthis.backend.domain.user.token.repository.BlacklistTokenRedisRepository;
import com.danthis.backend.domain.user.token.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenReader {

  private final RefreshTokenRedisRepository refreshTokenRedisRepository;
  private final BlacklistTokenRedisRepository blacklistTokenRedisRepository;

  public RefreshToken findRefreshTokenByUserId(Long userId) {
    return refreshTokenRedisRepository.findById(userId)
                                      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }

  public boolean isTokenBlacklisted(String token) {
    return blacklistTokenRedisRepository.existsById(token);
  }
}

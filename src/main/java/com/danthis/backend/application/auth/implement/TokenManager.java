package com.danthis.backend.application.auth.implement;

import com.danthis.backend.domain.user.token.BlacklistToken;
import com.danthis.backend.domain.user.token.RefreshToken;
import com.danthis.backend.domain.user.token.repository.BlacklistTokenRedisRepository;
import com.danthis.backend.domain.user.token.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenManager {

  private final RefreshTokenRedisRepository refreshTokenRedisRepository;
  private final BlacklistTokenRedisRepository blacklistTokenRedisRepository;

  public void saveRefreshToken(RefreshToken refreshToken) {
    refreshTokenRedisRepository.save(refreshToken);
  }

  public void deleteRefreshTokenByUserId(Long userId) {
    refreshTokenRedisRepository.deleteById(userId);
  }

  public void saveBlacklistToken(BlacklistToken blacklistToken) {
    blacklistTokenRedisRepository.save(blacklistToken);
  }
}

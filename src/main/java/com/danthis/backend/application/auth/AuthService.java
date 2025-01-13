package com.danthis.backend.application.auth;

import com.danthis.backend.application.auth.implement.KakaoLoginManager;
import com.danthis.backend.application.auth.implement.TokenManager;
import com.danthis.backend.application.auth.implement.TokenProvider;
import com.danthis.backend.application.auth.implement.TokenReader;
import com.danthis.backend.application.auth.request.TokenServiceRequest;
import com.danthis.backend.application.auth.response.TokenServiceResponse;
import com.danthis.backend.application.user.implement.UserManager;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.token.BlacklistToken;
import com.danthis.backend.domain.user.token.RefreshToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final KakaoLoginManager kakaoLoginManager;
  private final UserManager userManager;
  private final UserReader userReader;
  private final TokenProvider tokenProvider;
  private final TokenManager tokenManager;
  private final TokenReader tokenReader;

  @Transactional
  public TokenServiceResponse kakaoLogin(String code) throws JsonProcessingException {
    String token = kakaoLoginManager.getKakaoToken(code);
    User user = kakaoLoginManager.getKakaoUser(token);

    TokenServiceResponse tokenServiceResponse = tokenProvider.createToken(
        String.valueOf(user.getId()), user.getEmail(), "USER");

    saveRefreshToken(user, tokenServiceResponse);

    return tokenServiceResponse;
  }

  @Transactional
  public void logout(TokenServiceRequest tokenServiceRequest) {
    invalidateTokens(tokenServiceRequest);
  }

  @Transactional
  public void withdraw(Long userId, TokenServiceRequest tokenServiceRequest) {
    User user = userReader.readUserById(userId);
    userManager.withdrawUser(user);
    invalidateTokens(tokenServiceRequest);
  }

  @Transactional
  public TokenServiceResponse reissueAccessToken(TokenServiceRequest tokenServiceRequest) {
    String refreshToken = tokenServiceRequest.getRefreshToken();

    if (!tokenProvider.validate(refreshToken) || !tokenProvider.validateExpired(refreshToken)) {
      throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    User user = tokenProvider.getUserFromToken(refreshToken);
    RefreshToken findToken = tokenReader.findRefreshTokenByUserId(user.getId());

    tokenManager.deleteRefreshTokenByUserId(user.getId());

    TokenServiceResponse tokenServiceResponse = tokenProvider.createToken(
        String.valueOf(findToken.getUserId()),
        findToken.getEmail(),
        findToken.getAuthority()
    );

    saveRefreshToken(user, tokenServiceResponse);

    SecurityContextHolder.getContext()
                         .setAuthentication(
                             tokenProvider.getAuthentication(tokenServiceResponse.getAccessToken())
                         );

    return tokenServiceResponse;
  }

  private void invalidateTokens(TokenServiceRequest tokenServiceRequest) {
    String accessToken = tokenServiceRequest.getAccessToken();
    String refreshToken = tokenServiceRequest.getRefreshToken();

    if (accessToken == null || !tokenProvider.validate(accessToken)) {
      throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
    }

    long expiration = tokenProvider.getExpiration(accessToken);
    tokenManager.saveBlacklistToken(
        BlacklistToken.builder()
                      .token(accessToken)
                      .expiration(expiration / 1000)
                      .build()
    );

    Long userId = tokenProvider.getUserIdFromToken(refreshToken);
    tokenManager.deleteRefreshTokenByUserId(userId);

    SecurityContextHolder.clearContext();
  }

  private void saveRefreshToken(User user, TokenServiceResponse response) {
    tokenManager.saveRefreshToken(
        RefreshToken.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .authorities(Collections.singleton(
                        new SimpleGrantedAuthority("USER")))
                    .refreshToken(response.getRefreshToken())
                    .build()
    );
  }
}

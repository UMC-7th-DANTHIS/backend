package com.danthis.backend.application.auth.implement;

import com.danthis.backend.application.auth.response.TokenServiceResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.common.security.oauth.KakaoUserDetails;
import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.repository.UserRepository;
import com.danthis.backend.domain.user.token.repository.BlacklistTokenRedisRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

  private static final String AUTH_ID = "ID";
  private static final String AUTH_KEY = "AUTHORITY";
  private static final String AUTH_EMAIL = "EMAIL";

  private final UserRepository userRepository;
  private final BlacklistTokenRedisRepository blacklistTokenRedisRepository;

  private Key key;
  private final String secretKey;
  private final long accessExpirations;
  private final long refreshExpirations;

  public TokenProvider(@Value("${jwt.secret_key}") String secretKey,
      @Value("${jwt.access_expirations}") long accessExpirations,
      @Value("${jwt.refresh_expirations}") long refreshExpirations,
      BlacklistTokenRedisRepository blacklistTokenRedisRepository,
      UserRepository userRepository) {
    this.secretKey = secretKey;
    this.accessExpirations = accessExpirations * 1000;
    this.refreshExpirations = refreshExpirations * 1000;
    this.blacklistTokenRedisRepository = blacklistTokenRedisRepository;
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void initKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
  }

  public TokenServiceResponse createToken(String userId, String email, String role) {
    long now = (new Date()).getTime();

    Date accessValidity = new Date(now + this.accessExpirations);
    Date refreshValidity = new Date(now + this.refreshExpirations);

    String accessToken = Jwts.builder()
                             .addClaims(Map.of(AUTH_ID, userId))
                             .addClaims(Map.of(AUTH_EMAIL, email))
                             .addClaims(Map.of(AUTH_KEY, role))
                             .signWith(key, SignatureAlgorithm.HS256)
                             .setExpiration(accessValidity)
                             .compact();

    String refreshToken = Jwts.builder()
                              .addClaims(Map.of(AUTH_ID, userId))
                              .addClaims(Map.of(AUTH_EMAIL, email))
                              .addClaims(Map.of(AUTH_KEY, role))
                              .signWith(key, SignatureAlgorithm.HS256)
                              .setExpiration(refreshValidity)
                              .compact();

    return TokenServiceResponse.of(accessToken, refreshToken);
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

    List<String> authorities = Arrays.asList(claims.get(AUTH_KEY)
                                                   .toString()
                                                   .split(","));

    List<? extends GrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                                                                           .map(
                                                                               SimpleGrantedAuthority::new)
                                                                           .toList();

    KakaoUserDetails principal = new KakaoUserDetails(Long.parseLong((String) claims.get(AUTH_ID)),
        (String) claims.get(AUTH_EMAIL),
        simpleGrantedAuthorities, Map.of());

    return new UsernamePasswordAuthenticationToken(principal, token, simpleGrantedAuthorities);
  }

  public boolean validate(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);

      boolean isBlacklisted = blacklistTokenRedisRepository.existsById(token);
      return !isBlacklisted;
    } catch (SecurityException | MalformedJwtException | UnsupportedJwtException |
             IllegalArgumentException e) {
      return false;
    } catch (ExpiredJwtException e) {
      return true;
    }
  }

  public long getExpiration(String token) {
    Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
    Date expiration = claims.getExpiration();
    return expiration.getTime() - System.currentTimeMillis();
  }

  public boolean validateExpired(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      return false;
    }
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
    return Long.parseLong((String) claims.get(AUTH_ID));
  }

  public User getUserFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

    Long userId = Long.parseLong(claims.get("ID").toString());

    return userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }
}

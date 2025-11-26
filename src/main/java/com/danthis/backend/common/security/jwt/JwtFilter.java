package com.danthis.backend.common.security.jwt;

import com.danthis.backend.application.auth.implement.TokenProvider;
import com.danthis.backend.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  @Value("${jwt.access_header}")
  private String accessTokenHeader;
  private final String BEARER = "Bearer ";
  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      @Nullable HttpServletResponse response,
      @Nullable FilterChain filterChain) throws ServletException, IOException {

    String uri = request.getRequestURI();
    String method = request.getMethod();
    log.info("Request - Method: {}, URI: {}", method, uri);

    // Pass URI 체크
    if (isRequestPassURI(request)) {
      log.debug("Pass URI detected: {}", uri);
      Objects.requireNonNull(filterChain).doFilter(request, response);
      return;
    }

    // 액세스 토큰 추출
    String accessToken = extractAccessToken(request).orElse(null);

    // 토큰 검증
    if (accessToken == null) {
      log.warn("No access token found for URI: {}", uri);
      setErrorResponse(response);
      return;
    }

    if (!tokenProvider.validate(accessToken)) {
      log.warn("Invalid access token for URI: {}", uri);
      setErrorResponse(response);
      return;
    }

    if (!tokenProvider.validateExpired(accessToken)) {
      log.warn("Expired access token for URI: {}", uri);
      setErrorResponse(response);
      return;
    }

    // 인증 정보 설정
    SecurityContextHolder.getContext()
                         .setAuthentication(tokenProvider.getAuthentication(accessToken));

    Objects.requireNonNull(filterChain).doFilter(request, response);
  }

  private void setErrorResponse(HttpServletResponse response) throws IOException {
    Objects.requireNonNull(response)
           .setStatus(ErrorCode.INVALID_ACCESS_TOKEN.getHttpStatus().value());
    response.setContentType("application/json; charset=UTF-8");

    ObjectMapper objectMapper = new ObjectMapper();
    response.getWriter().write(objectMapper.writeValueAsString(Map.of(
        "status", ErrorCode.INVALID_ACCESS_TOKEN.getHttpStatus().value(),
        "error", ErrorCode.INVALID_ACCESS_TOKEN.getMessage()
    )));
  }

  private static boolean isRequestPassURI(HttpServletRequest request) {
    String uri = request.getRequestURI();
    String method = request.getMethod();

    // 최상위 경로
    if ("/".equals(uri)) {
      return true;
    }

    // Actuator health
    if (uri.startsWith("/actuator/health")) {
      return true;
    }

    // 예외 처리 경로
    if (uri.startsWith("/exception")) {
      return true;
    }

    // 공개 API (GET)
    if ("GET".equals(method)) {
      if (uri.startsWith("/dancers/all") ||
          uri.startsWith("/dance-classes/all") ||
          uri.equals("/auth/reissue") ||
          uri.startsWith("/dancers/info") ||
          uri.startsWith("/dance-classes/info") ||
          uri.startsWith("/community/info") ||
          uri.startsWith("/dancers/genres") ||
          uri.startsWith("/practice-rooms/info") ||
          uri.startsWith("/search")) {
        return true;
      }
    }

    // 인증 관련 경로 (POST)
    if ("POST".equals(method)) {
      if (uri.equals("/auth/login/kakao") ||
          uri.equals("/auth/logout")) {
        return true;
      }
    }

    // OPTIONS 요청은 모두 통과
    if ("OPTIONS".equals(method)) {
      return true;
    }

    // 회원 탈퇴는 인증 필요 (DELETE /auth/withdraw)

    return false;
  }

  public Optional<String> extractAccessToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(accessTokenHeader))
                   .filter(accessToken -> accessToken.startsWith(BEARER))
                   .map(accessToken -> accessToken.replace(BEARER, ""));
  }
}

package com.danthis.backend.common.websocket;

import com.danthis.backend.application.auth.implement.TokenProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Slf4j
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

  private final TokenProvider tokenProvider;

  @Override
  public boolean beforeHandshake(ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Map<String, Object> attributes) {

    String auth = request.getHeaders().getFirst("Authorization");

    if (!StringUtils.hasText(auth) || !auth.startsWith("Bearer ")) {
      log.warn("[WS] Handshake rejected: Authorization header missing or invalid");
      return false;
    }

    String token = auth.substring("Bearer ".length());

    try {
      Long userId = tokenProvider.getUserIdFromToken(token);
      attributes.put("userId", userId);
      attributes.put("accessToken", token);
      return true;

    } catch (Exception e) {
      log.warn("[WS] Handshake rejected: invalid token", e);
      return false;
    }
  }

  @Override
  public void afterHandshake(ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Exception exception) {
  }
}

package com.danthis.backend.common.config;

import com.danthis.backend.api.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private final WebSocketChatHandler webSocketChatHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketChatHandler, "/ws/chat")
            .setAllowedOrigins("*") // "/ws/chat" 경로로 웹소켓 연결 허용
            .addInterceptors(new HttpSessionHandshakeInterceptor());  // HTTP 세션 정보를 웹소켓으로 전달
    //TODO: JWT 검증 인터셉터 추가
  }
}

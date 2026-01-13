package com.danthis.backend.common.config;

import com.danthis.backend.api.WebSocketChatHandler;
import com.danthis.backend.application.auth.implement.TokenProvider;
import com.danthis.backend.common.websocket.JwtHandshakeInterceptor;
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
  private final TokenProvider tokenProvider;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketChatHandler, "/ws/chat")
            .setAllowedOrigins(
                "https://danthis.site",
                "https://www.danthis.site",
                "http://localhost:3000",
                "http://localhost:8080",
                "http://localhost"
            )
            .addInterceptors(
                new HttpSessionHandshakeInterceptor(),
                new JwtHandshakeInterceptor(tokenProvider)
            );
  }
}

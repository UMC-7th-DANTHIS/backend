package com.danthis.backend.common.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.danthis.backend.application.auth.oauth.KakaoUserDetailsService;
import com.danthis.backend.common.security.jwt.JwtAccessDeniedHandler;
import com.danthis.backend.common.security.jwt.JwtAuthenticationFailEntryPoint;
import com.danthis.backend.common.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFailEntryPoint jwtAuthenticationFailEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtFilter jwtFilter;
  private final KakaoUserDetailsService kakaoUserDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(withDefaults())
        .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(oauth -> oauth
            // 1) 인가 요청 시작 경로 변경
            .authorizationEndpoint(authz ->
                authz.baseUri("/auth/authorize/kakao")
            )
            // 2) 카카오 인가 코드 콜백 경로 변경
            .redirectionEndpoint(redir ->
                redir.baseUri("/auth/login/kakao")
            )
            // 3) 사용자 정보 조회 서비스
            .userInfoEndpoint(userInfo ->
                userInfo.userService(kakaoUserDetailsService)
            )
        )
        .authorizeHttpRequests(auth -> auth
            // 4) 콜백 URI(GET)를 포함해 /auth/** 전체를 공개
            .requestMatchers(HttpMethod.GET,
                "/",
                "/actuator/health",
                "/auth/**",
                "/exception/**",
                "/dance-classes/all",
                "/dancers/all"
            ).permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ex -> {
          ex.authenticationEntryPoint(jwtAuthenticationFailEntryPoint);
          ex.accessDeniedHandler(jwtAccessDeniedHandler);
        });

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

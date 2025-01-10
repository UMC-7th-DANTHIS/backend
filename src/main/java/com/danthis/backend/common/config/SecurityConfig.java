package com.danthis.backend.common.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.danthis.backend.application.auth.oauth.KakaoUserDetailsService;
import com.danthis.backend.common.security.jwt.JwtAccessDeniedHandler;
import com.danthis.backend.common.security.jwt.JwtAuthenticationFailEntryPoint;
import com.danthis.backend.common.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
    http.formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(withDefaults())
        .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(
            oauth -> oauth.userInfoEndpoint(config -> config.userService(kakaoUserDetailsService)))
        .authorizeHttpRequests(request -> request
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/exception/**").permitAll()
//            .requestMatchers("/swagger-ui/**").permitAll()
//            .requestMatchers("/api-docs/**").permitAll()
//            .requestMatchers(HttpMethod.POST, "/posts").permitAll()
//            .requestMatchers(HttpMethod.GET, "/posts/*").permitAll()
//            .requestMatchers(HttpMethod.PATCH, "/posts/*/summary").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptionHandling -> {
          exceptionHandling.authenticationEntryPoint(jwtAuthenticationFailEntryPoint);
          exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler);
        });

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

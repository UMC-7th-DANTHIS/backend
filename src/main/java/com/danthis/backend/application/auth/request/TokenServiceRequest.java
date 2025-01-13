package com.danthis.backend.application.auth.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenServiceRequest {
  private String accessToken;
  private String refreshToken;
}

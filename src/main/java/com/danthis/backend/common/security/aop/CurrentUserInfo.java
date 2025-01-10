package com.danthis.backend.common.security.aop;

import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentUserInfo {

  @Null
  private Long userId;
}

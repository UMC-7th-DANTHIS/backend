package com.danthis.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

  private final int code;
  private final String message;

  public static ErrorResponse of(int code, String message) {
    return new ErrorResponse(code, message);
  }
}

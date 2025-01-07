package com.danthis.backend.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

  private final int code;
  private final String message;
  private final T data;

  private ApiResponse(HttpStatus status, String message, T data) {
    this.code = status.value();
    this.message = message;
    this.data = data;
  }

  public static <T> ApiResponse<T> OK(T data) {
    return new ApiResponse<>(HttpStatus.OK, "요청이 성공적으로 처리되었습니다.", data);
  }
}

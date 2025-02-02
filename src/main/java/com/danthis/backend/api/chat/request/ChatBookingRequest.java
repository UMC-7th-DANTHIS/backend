package com.danthis.backend.api.chat.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChatBookingRequest {

  @NotNull(message = "userId는 필수입니다.")
  private Long userId;

  @NotNull(message = "classId는 필수입니다.")
  private Long classId;
}

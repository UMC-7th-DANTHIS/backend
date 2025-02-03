package com.danthis.backend.application.chat.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatBookingServiceResponse {

  private Long bookingId;
  private Long userId;
  private Long classId;
  private LocalDateTime bookingDate;
  private Boolean isApproved;
}

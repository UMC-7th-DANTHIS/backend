package com.danthis.backend.application.chat.implement;

import com.danthis.backend.application.chat.response.ChatBookingServiceResponse;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMapper {

  public ChatBookingServiceResponse toChatBookingResponse(DanceClassBooking booking) {
    return ChatBookingServiceResponse.builder()
                              .bookingId(booking.getId())
                              .userId(booking.getUser().getId())
                              .classId(booking.getDanceClass().getId())
                              .bookingDate(booking.getBookingDate())
                              .isApproved(booking.getIsApproved())
                              .build();
  }
}

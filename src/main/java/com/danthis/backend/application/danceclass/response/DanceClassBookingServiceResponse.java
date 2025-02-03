package com.danthis.backend.application.danceclass.response;

import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DanceClassBookingServiceResponse {

  private Long classId;
  private List<UserSummary> approvedUsers;

  @Getter
  @Builder
  public static class UserSummary {

    private Long userId;
    private String nickname;
    private String profileImage;
  }

  public static DanceClassBookingServiceResponse from(Long classId,
      List<DanceClassBooking> bookings) {
    return DanceClassBookingServiceResponse.builder()
                                           .classId(classId)
                                           .approvedUsers(
                                               bookings.stream()
                                                       .map(booking -> UserSummary.builder()
                                                                                  .userId(booking.getUser().getId())
                                                                                  .nickname(booking.getUser().getNickname())
                                                                                  .profileImage(booking.getUser().getProfileImage())
                                                                                  .build())
                                                       .toList())
                                           .build();
  }
}

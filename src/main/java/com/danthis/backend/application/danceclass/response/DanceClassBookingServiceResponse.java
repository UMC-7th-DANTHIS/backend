package com.danthis.backend.application.danceclass.response;

import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class DanceClassBookingServiceResponse {

  private Long classId;
  private List<UserSummary> approvedUsers;
  private int currentPage;
  private int totalPages;
  private long totalUsers;

  @Getter
  @Builder
  public static class UserSummary {

    private Long userId;
    private String nickname;
    private String profileImage;
  }

  public static DanceClassBookingServiceResponse from(Long classId, Page<DanceClassBooking> bookings) {
    return DanceClassBookingServiceResponse.builder()
                                           .classId(classId)
                                           .approvedUsers(
                                               bookings.getContent()
                                                       .stream()
                                                       .map(booking -> UserSummary.builder()
                                                                                  .userId(booking.getUser().getId())
                                                                                  .nickname(booking.getUser().getNickname())
                                                                                  .profileImage(booking.getUser().getProfileImage())
                                                                                  .build())
                                                       .toList())
                                           .currentPage(bookings.getNumber() + 1)
                                           .totalPages(bookings.getTotalPages())
                                           .totalUsers(bookings.getTotalElements())
                                           .build();
  }
}

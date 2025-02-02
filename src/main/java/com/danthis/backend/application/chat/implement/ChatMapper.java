package com.danthis.backend.application.chat.implement;

import com.danthis.backend.application.chat.response.ChatBookingServiceResponse;
import com.danthis.backend.application.chat.response.DancerChatListServiceResponse;
import com.danthis.backend.application.chat.response.UserChatListServiceResponse;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.user.User;
import java.util.List;
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

  public DancerChatListServiceResponse toDancerChatListResponse(Dancer dancer,
      List<DanceClassBooking> chatBookings) {
    List<DancerChatListServiceResponse.ChatUserSummary> users = chatBookings.stream()
                                                                            .map(booking -> DancerChatListServiceResponse.ChatUserSummary.builder()
                                                                                                                                        .userId(booking.getUser().getId())
                                                                                                                                        .nickname(booking.getUser().getNickname())
                                                                                                                                        .profileImage(booking.getUser().getProfileImage())
                                                                                                                                        .latestBookingDate(booking.getBookingDate())
                                                                                                                                        .build())
                                                                            .toList();

    return DancerChatListServiceResponse.builder()
                                        .dancerId(dancer.getId())
                                        .chats(users)
                                        .build();
  }

  public UserChatListServiceResponse toUserChatListResponse(User user,
      List<DanceClassBooking> chatBookings) {
    List<UserChatListServiceResponse.ChatDancerSummary> dancers = chatBookings.stream()
                                                                              .map(booking -> UserChatListServiceResponse.ChatDancerSummary.builder()
                                                                                                                                          .chatId(booking.getId())
                                                                                                                                          .dancerId(booking.getDanceClass().getDancer().getId())
                                                                                                                                          .dancerName(booking.getDanceClass().getDancer().getDancerName())
                                                                                                                                          .profileImage(booking.getDanceClass().getDancer().getProfileImage())
                                                                                                                                          .build())
                                                                              .toList();

    return UserChatListServiceResponse.builder()
                                      .userId(user.getId())
                                      .chatList(dancers)
                                      .build();
  }
}

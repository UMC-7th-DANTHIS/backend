package com.danthis.backend.application.chat.implement;

import com.danthis.backend.application.chat.response.ChatBookingServiceResponse;
import com.danthis.backend.application.chat.response.DancerChatListServiceResponse;
import com.danthis.backend.application.chat.response.UserChatListServiceResponse;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

  public DancerChatListServiceResponse toDancerChatListServiceResponse(Dancer dancer, Page<DancerUserChat> chatPage) {
    List<DancerChatListServiceResponse.ChatUserSummary> users = chatPage.getContent().stream()
                                                                        .map(chat -> DancerChatListServiceResponse.ChatUserSummary.builder()
                                                                                                                                  .userId(chat.getUser().getId())
                                                                                                                                  .nickname(chat.getUser().getNickname())
                                                                                                                                  .profileImage(chat.getUser().getProfileImage())
                                                                                                                                  .build())
                                                                        .toList();

    return DancerChatListServiceResponse.builder()
                                        .dancerId(dancer.getId())
                                        .currentPage(chatPage.getNumber() + 1)
                                        .totalPages(chatPage.getTotalPages())
                                        .totalUsers((int) chatPage.getTotalElements())
                                        .chatUsers(users)
                                        .build();
  }

  public UserChatListServiceResponse toUserChatListResponse(User user, Page<DanceClassBooking> chatBookings) {
    List<UserChatListServiceResponse.ChatDancerSummary> dancers = chatBookings.getContent().stream()
                                                                              .map(booking -> UserChatListServiceResponse.ChatDancerSummary.builder()
                                                                                                                                           .chatId(booking.getId())
                                                                                                                                           .dancerId(booking.getDanceClass().getDancer().getId())
                                                                                                                                           .dancerName(booking.getDanceClass().getDancer().getDancerName())
                                                                                                                                           .profileImage(booking.getDanceClass().getDancer().getProfileImage())
                                                                                                                                           .build())
                                                                              .toList();

    return UserChatListServiceResponse.builder()
                                      .userId(user.getId())
                                      .currentPage(chatBookings.getNumber() + 1)
                                      .totalPages(chatBookings.getTotalPages())
                                      .totalDancers((int) chatBookings.getTotalElements())
                                      .chatList(dancers)
                                      .build();
  }
}

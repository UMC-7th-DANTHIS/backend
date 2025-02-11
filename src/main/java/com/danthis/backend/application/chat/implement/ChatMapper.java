package com.danthis.backend.application.chat.implement;

import com.danthis.backend.application.chat.response.DancerChatListServiceResponse;
import com.danthis.backend.application.chat.response.UserChatListServiceResponse;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMapper {

  public DancerChatListServiceResponse toDancerChatListServiceResponse(Dancer dancer,
      Page<DancerUserChat> chatPage) {
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

  public UserChatListServiceResponse toUserChatListResponse(User user,
      Page<DancerUserChat> chatPage) {
    List<UserChatListServiceResponse.ChatDancerSummary> dancers = chatPage.getContent().stream()
                                                                          .map(chat -> UserChatListServiceResponse.ChatDancerSummary.builder()
                                                                                                                                   .dancerId(chat.getDancer().getId())
                                                                                                                                   .dancerName(chat.getDancer().getDancerName())
                                                                                                                                   .profileImage(chat.getDancer().getProfileImage())
                                                                                                                                   .build())
                                                                          .toList();

    return UserChatListServiceResponse.builder()
                                      .userId(user.getId())
                                      .currentPage(chatPage.getNumber() + 1)
                                      .totalPages(chatPage.getTotalPages())
                                      .totalDancers((int) chatPage.getTotalElements())
                                      .chatList(dancers)
                                      .build();
  }
}

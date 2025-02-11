package com.danthis.backend.application.chat;

import com.danthis.backend.application.chat.implement.ChatManager;
import com.danthis.backend.application.chat.implement.ChatMapper;
import com.danthis.backend.application.chat.implement.ChatReader;
import com.danthis.backend.application.chat.response.DancerChatListServiceResponse;
import com.danthis.backend.application.chat.response.UserChatListServiceResponse;
import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatReader chatReader;
  private final ChatManager chatManager;
  private final ChatMapper chatMapper;
  private final DancerReader dancerReader;
  private final UserReader userReader;

  @Transactional
  public void startChatWithDancer(Long userId, Long dancerId) {
    User user = chatReader.readUserById(userId);
    Dancer dancer = chatReader.readDancerById(dancerId);

    chatManager.startChat(user, dancer);
  }

  @Transactional
  public DancerChatListServiceResponse getDancerChatList(Long userId, int page, int size) {
    Dancer dancer = dancerReader.readDancerByUserId(userId);

    if (dancer == null) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    Page<DancerUserChat> chatUsersPage = chatReader.readChatsByDancer(dancer, page, size);

    return chatMapper.toDancerChatListServiceResponse(dancer, chatUsersPage);
  }

  @Transactional
  public UserChatListServiceResponse getUserChatList(Long userId, int page, int size) {
    validateUserOrThrow(userId);

    User user = userReader.readUserById(userId);
    Page<DanceClassBooking> chatBookings = chatReader.readBookingsByUser(user, page, size);

    return chatMapper.toUserChatListResponse(user, chatBookings);
  }


  private void validateUserOrThrow(Long userId) {
    if (!chatReader.isUser(userId)) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }
  }
}

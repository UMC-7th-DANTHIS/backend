package com.danthis.backend.application.chat;

import com.danthis.backend.api.chat.request.ChatBookingRequest;
import com.danthis.backend.application.chat.implement.ChatManager;
import com.danthis.backend.application.chat.implement.ChatMapper;
import com.danthis.backend.application.chat.implement.ChatReader;
import com.danthis.backend.application.chat.response.ChatBookingServiceResponse;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatReader chatReader;
  private final ChatManager chatManager;
  private final ChatMapper chatMapper;

  @Transactional
  public ChatBookingServiceResponse createChatBooking(ChatBookingRequest request) {
    User user = chatReader.readUserById(request.getUserId());
    DanceClass danceClass = chatReader.readDanceClassById(request.getClassId());
    Dancer dancer = danceClass.getDancer();

    DanceClassBooking booking = chatManager.createBooking(user, danceClass, dancer);
    return chatMapper.toChatBookingResponse(booking);
  }
}

package com.danthis.backend.application.chat.implement;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceclassbooking.repository.DanceClassBookingRepository;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.mapping.danceruserchat.repository.DancerUserChatRepository;
import com.danthis.backend.domain.user.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatManager {

  private final DanceClassBookingRepository danceClassBookingRepository;
  private final DancerUserChatRepository dancerUserChatRepository;

  public void startChat(User user, Dancer dancer) {
    Optional<DancerUserChat> existingChat = dancerUserChatRepository.findByDancerAndUser(dancer, user);

    if (existingChat.isEmpty()) {
      DancerUserChat chat = DancerUserChat.createChat(dancer, user);
      dancerUserChatRepository.save(chat);
    }
  }
}

package com.danthis.backend.application.chat.implement;

import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageReader {

  private final UserReader userReader;
  private final DancerReader dancerReader;

  public User readUserById(Long userId) {
    return userReader.readUserById(userId);
  }

  public Dancer readDancerById(Long dancerId) {
    return dancerReader.readDancerById(dancerId);
  }
}

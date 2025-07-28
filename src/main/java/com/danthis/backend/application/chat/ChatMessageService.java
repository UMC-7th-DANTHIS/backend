package com.danthis.backend.application.chat;

import com.danthis.backend.api.chat.response.ChatRoomCreateResponseDTO;
import com.danthis.backend.application.chat.implement.ChatMessageManager;
import com.danthis.backend.application.chat.implement.ChatMessageMapper;
import com.danthis.backend.application.chat.implement.ChatMessageReader;
import com.danthis.backend.domain.chat.ChatRoom;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

  private final ChatMessageManager chatMessageManager;
  private final ChatMessageMapper chatMessageMapper;
  private final ChatMessageReader chatMessageReader;

  public ChatRoomCreateResponseDTO startChat(Long userId, Long dancerId) {
    User user = chatMessageReader.readUserById(userId);
    Dancer dancer = chatMessageReader.readDancerById(dancerId);

    ChatRoom chatRoom = chatMessageManager.createChatRoomIfNotExists(user, dancer);

    return chatMessageMapper.toChatRoomCreateResponseDTO(chatRoom);
  }

  public Long ensureChatRoomExists(Long userId, Long dancerId) {
    User user = chatMessageReader.readUserById(userId);
    Dancer dancer = chatMessageReader.readDancerById(dancerId);

    return chatMessageManager.createChatRoomIfNotExists(user, dancer).getId();
  }

  public void saveMessage(User sender, Long chatRoomId, String content) {
    ChatRoom chatRoom = chatMessageManager.getChatRoomById(chatRoomId);
    chatMessageManager.saveChatMessage(sender, chatRoom, content);
  }

  public User getUserById(Long userId) {
    return chatMessageReader.readUserById(userId);
  }
}

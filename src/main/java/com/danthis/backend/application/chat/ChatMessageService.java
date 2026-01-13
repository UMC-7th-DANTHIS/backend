package com.danthis.backend.application.chat;

import com.danthis.backend.api.chat.response.ChatRoomCreateResponseDTO;
import com.danthis.backend.application.chat.implement.ChatMessageManager;
import com.danthis.backend.application.chat.implement.ChatMessageMapper;
import com.danthis.backend.application.chat.implement.ChatMessageReader;
import com.danthis.backend.application.chat.response.ChatMessageResponseDTO;
import com.danthis.backend.domain.chat.ChatMessage;
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

  public ChatMessageResponseDTO saveMessage(Long senderId, Long chatRoomId, String content) {
    User sender = chatMessageReader.readUserById(senderId);
    ChatRoom chatRoom = chatMessageManager.getChatRoomById(chatRoomId);

    ChatMessage chatMessage = chatMessageManager.saveChatMessage(sender, chatRoom, content);

    return ChatMessageResponseDTO.builder()
                                 .messageId(chatMessage.getId())
                                 .senderId(sender.getId())
                                 .chatRoomId(chatRoomId)
                                 .senderNickname(sender.getNickname())
                                 .message(content)
                                 .sentAt(chatMessage.getCreatedAt())
                                 .build();
  }

  public Long resolveOpponentId(Long chatRoomId, Long senderId) {
    ChatRoom chatRoom = chatMessageManager.getChatRoomById(chatRoomId);

    if (chatRoom.getUser().getId().equals(senderId)) {
      return chatRoom.getDancer().getId();
    }
    return chatRoom.getUser().getId();
  }
}

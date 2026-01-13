package com.danthis.backend.application.chat.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.chat.ChatMessage;
import com.danthis.backend.domain.chat.ChatRoom;
import com.danthis.backend.domain.chat.repository.ChatMessageRepository;
import com.danthis.backend.domain.chat.repository.ChatRoomRepository;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageManager {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatMessageRepository chatMessageRepository;

  // 유저-댄서 1:1 채팅방 생성
  public ChatRoom createChatRoomIfNotExists(User user, Dancer dancer) {
    return chatRoomRepository.findByUserAndDancer(user, dancer)
                             .orElseGet(() -> chatRoomRepository.save(ChatRoom.builder()
                                                                              .user(user)
                                                                              .dancer(dancer)
                                                                              .build()
                             ));
  }

  // 채팅 메시지 저장
  public ChatMessage saveChatMessage(User sender, ChatRoom chatRoom, String content) {
    ChatMessage chatMessage = ChatMessage.builder()
                                         .chatRoom(chatRoom)
                                         .user(sender)
                                         .content(content)
                                         .build();

    return chatMessageRepository.save(chatMessage);
  }

  // 채팅방 조회
  public ChatRoom getChatRoomById(Long chatRoomId) {
    return chatRoomRepository.findById(chatRoomId)
                             .orElseThrow(() -> new BusinessException(ErrorCode.CHATROOM_NOT_FOUND));
  }
}

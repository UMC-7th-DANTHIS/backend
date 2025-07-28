package com.danthis.backend.application.chat.implement;

import com.danthis.backend.api.chat.response.ChatRoomCreateResponseDTO;
import com.danthis.backend.domain.chat.ChatRoom;
import com.danthis.backend.domain.dancer.Dancer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageMapper {

  public ChatRoomCreateResponseDTO toChatRoomCreateResponseDTO(ChatRoom chatRoom) {
    Dancer dancer = chatRoom.getDancer();

    return ChatRoomCreateResponseDTO.builder()
                                    .chatRoomId(chatRoom.getId())
                                    .dancerId(dancer.getId())
                                    .dancerNickname(dancer.getDancerName())
                                    .dancerProfileImage(dancer.getProfileImage())
                                    .build();
  }
}

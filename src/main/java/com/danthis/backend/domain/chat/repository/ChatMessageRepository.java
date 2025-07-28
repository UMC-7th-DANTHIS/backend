package com.danthis.backend.domain.chat.repository;

import com.danthis.backend.domain.chat.ChatMessage;
import com.danthis.backend.domain.chat.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  List<ChatMessage> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);
}

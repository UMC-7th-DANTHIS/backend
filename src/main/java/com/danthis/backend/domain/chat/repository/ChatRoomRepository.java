package com.danthis.backend.domain.chat.repository;

import com.danthis.backend.domain.chat.ChatRoom;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  Optional<ChatRoom> findByUserAndDancer(User user, Dancer dancer);
}

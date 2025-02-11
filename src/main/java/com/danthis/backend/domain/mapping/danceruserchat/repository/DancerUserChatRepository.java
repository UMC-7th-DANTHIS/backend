package com.danthis.backend.domain.mapping.danceruserchat.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DancerUserChatRepository extends JpaRepository<DancerUserChat, Long> {

  Optional<DancerUserChat> findByDancerAndUser(Dancer dancer, User user);

  List<DancerUserChat> findAllByDancer(Dancer dancer);

  List<DancerUserChat> findAllByUser(User user);
}

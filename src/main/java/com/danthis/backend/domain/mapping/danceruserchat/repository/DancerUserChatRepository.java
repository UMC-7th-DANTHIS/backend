package com.danthis.backend.domain.mapping.danceruserchat.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.user.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DancerUserChatRepository extends JpaRepository<DancerUserChat, Long> {

  Optional<DancerUserChat> findByDancerAndUser(Dancer dancer, User user);

  Page<DancerUserChat> findByDancer(Dancer dancer, Pageable pageable);

  Page<DancerUserChat> findByUser(User user, Pageable pageable);
}

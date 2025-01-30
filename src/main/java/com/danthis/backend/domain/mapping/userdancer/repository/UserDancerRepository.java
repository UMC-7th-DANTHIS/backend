package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDancerRepository extends JpaRepository<UserDancer, Long> {

  void deleteByUser(User user);

  List<UserDancer> findByUser(User user);
}

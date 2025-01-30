package com.danthis.backend.domain.mapping.usergenre.repository;

import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.danthis.backend.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {

  void deleteByUser(User user);

  List<UserGenre> findByUser(User user);
}

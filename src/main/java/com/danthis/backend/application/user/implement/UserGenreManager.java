package com.danthis.backend.application.user.implement;

import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.danthis.backend.domain.mapping.usergenre.repository.UserGenreRepository;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGenreManager {

  private final UserGenreRepository userGenreRepository;

  @Transactional
  public void saveAll(Set<UserGenre> userGenres) {
    userGenreRepository.saveAll(userGenres);
  }

  @Transactional
  public void deleteByUser(User user) {
    userGenreRepository.deleteByUser(user);
  }
}

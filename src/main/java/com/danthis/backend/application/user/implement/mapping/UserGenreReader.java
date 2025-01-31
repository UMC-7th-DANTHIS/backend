package com.danthis.backend.application.user.implement.mapping;

import com.danthis.backend.domain.mapping.usergenre.repository.UserGenreRepository;
import com.danthis.backend.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGenreReader {

  private final UserGenreRepository userGenreRepository;

  public List<Long> findGenreIdsByUser(User user) {
    return userGenreRepository.findByUser(user.getId())
                              .stream()
                              .map(userGenre -> userGenre.getGenre().getId())
                              .toList();
  }
}

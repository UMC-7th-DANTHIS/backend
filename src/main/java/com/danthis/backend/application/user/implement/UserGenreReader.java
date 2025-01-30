package com.danthis.backend.application.user.implement;

import com.danthis.backend.domain.mapping.usergenre.repository.UserGenreRepository;
import com.danthis.backend.domain.user.User;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGenreReader {

  private final UserGenreRepository userGenreRepository;

  public Set<Long> findGenreIdsByUser(User user) {
    return userGenreRepository.findByUser(user).stream()
                              .map(userGenre -> userGenre.getGenre().getId())
                              .collect(Collectors.toSet());
  }
}

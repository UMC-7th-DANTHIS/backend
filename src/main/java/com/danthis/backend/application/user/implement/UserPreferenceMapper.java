package com.danthis.backend.application.user.implement;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.genre.repository.GenreRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPreferenceMapper {

  private final GenreRepository genreRepository;
  private final DancerRepository dancerRepository;

  public Set<Genre> mapToGenres(Set<Long> genreIds) {
    return genreIds.stream()
                   .map(genreRepository::findById)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .collect(Collectors.toSet());
  }

  public Set<Dancer> mapToDancers(Set<Long> dancerIds) {
    return dancerIds.stream()
                    .map(dancerRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
  }
}

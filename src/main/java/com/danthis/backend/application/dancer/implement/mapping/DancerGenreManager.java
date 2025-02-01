package com.danthis.backend.application.dancer.implement.mapping;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.dancergenre.repository.DancerGenreRepository;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerGenreManager {

  private final DancerGenreRepository dancerGenreRepository;

  @Transactional
  public void saveAll(Set<DancerGenre> dancerGenres) {
    dancerGenreRepository.saveAll(dancerGenres);
  }

  @Transactional
  public void deleteByDancer(Dancer dancer) {
    dancerGenreRepository.deleteByDancer(dancer.getId());
  }
}

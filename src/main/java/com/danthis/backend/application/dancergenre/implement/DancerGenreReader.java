package com.danthis.backend.application.dancergenre.implement;

import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.dancergenre.repository.DancerGenreRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerGenreReader {

  private final DancerGenreRepository dancerGenreRepository;

  public Page<DancerGenre> readDancerGenresByGenreId(Long genreId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return dancerGenreRepository.findByGenreId(genreId, pageable);
  }
}

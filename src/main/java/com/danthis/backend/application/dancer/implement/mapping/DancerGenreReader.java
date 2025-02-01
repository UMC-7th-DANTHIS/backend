package com.danthis.backend.application.dancer.implement.mapping;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.dancergenre.repository.DancerGenreRepository;

import java.util.List;
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

  public List<Long> findGenreIdByDancer(Dancer dancer) {
    return dancerGenreRepository.findByDancer(dancer.getId()).stream()
                                .map(dancerGenre -> dancerGenre.getGenre().getId())
                                .toList();
  }
}

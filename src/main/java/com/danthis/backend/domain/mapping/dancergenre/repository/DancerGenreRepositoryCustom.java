package com.danthis.backend.domain.mapping.dancergenre.repository;

import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DancerGenreRepositoryCustom {

  void deleteByDancer(Long dancerId);

  List<DancerGenre> findByDancer(Long dancerId);

  Page<DancerGenre> findByGenreId(Long genreId, Pageable pageable);
}

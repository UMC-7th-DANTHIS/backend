package com.danthis.backend.domain.mapping.dancergenre.repository;

import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DancerGenreRepositoryCustom {

  Page<DancerGenre> findByGenreId(Long genreId, Pageable pageable);
}

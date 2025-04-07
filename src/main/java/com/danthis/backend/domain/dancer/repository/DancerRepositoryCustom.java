package com.danthis.backend.domain.dancer.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DancerRepositoryCustom {

  Page<Dancer> searchByDancerName(String query, Pageable pageable);

  List<Dancer> findByGenres(Set<Long> genreList);
}

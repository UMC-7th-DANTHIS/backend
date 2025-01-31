package com.danthis.backend.domain.danceclass.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Long>,
    DanceClassRepositoryCustom {

  Page<DanceClass> findByGenreId(Long genreId, Pageable pageable);
}

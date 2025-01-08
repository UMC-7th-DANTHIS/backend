package com.danthis.backend.domain.mapping.dancergenre.repository;

import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DancerGenreRepository extends JpaRepository<DancerGenre, Long> {
}

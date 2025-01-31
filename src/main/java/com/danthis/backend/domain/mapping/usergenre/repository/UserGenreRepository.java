package com.danthis.backend.domain.mapping.usergenre.repository;

import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, Long>, UserGenreRepositoryCustom {
}

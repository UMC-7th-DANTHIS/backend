package com.danthis.backend.domain.mapping.danceclasshashtag.repository;

import com.danthis.backend.domain.danceclass.repository.DanceClassRepositoryCustom;
import com.danthis.backend.domain.mapping.danceclasshashtag.DanceClassHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassHashtagRepository extends JpaRepository<DanceClassHashtag, Long>,
    DanceClassRepositoryCustom {

}

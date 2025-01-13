package com.danthis.backend.domain.danceclass.danceclassimage.repository;

import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassImageRepository extends JpaRepository<DanceClassImage, Long>,
    DanceClassRepositoryCustom {

}

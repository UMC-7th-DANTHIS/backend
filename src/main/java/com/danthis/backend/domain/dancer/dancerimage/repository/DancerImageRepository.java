package com.danthis.backend.domain.dancer.dancerimage.repository;

import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DancerImageRepository extends JpaRepository<DancerImage, Long> {

}

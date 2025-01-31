package com.danthis.backend.domain.dancer.dancerimage.repository;

import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import java.util.List;

public interface DancerImageRepositoryCustom {

  void deleteByDancer(Long dancerId);

  List<DancerImage> findByDancer(Long dancerId);
}

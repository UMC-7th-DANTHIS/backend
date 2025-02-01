package com.danthis.backend.application.dancer.implement.mapping;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.dancerimage.repository.DancerImageRepository;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerImageManager {

  private final DancerImageRepository dancerImageRepository;

  @Transactional
  public void saveAll(Set<DancerImage> dancerImages) {
    dancerImageRepository.saveAll(dancerImages);
  }

  @Transactional
  public void deleteByDancer(Dancer dancer) {
    dancerImageRepository.deleteByDancer(dancer.getId());
  }
}

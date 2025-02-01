package com.danthis.backend.application.dancer.implement.mapping;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.dancerimage.repository.DancerImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerImageReader {

  private final DancerImageRepository dancerImageRepository;

  public List<String> findImageUrlByDancer(Dancer dancer) {
    return dancerImageRepository.findByDancer(dancer.getId()).stream()
                                .map(DancerImage::getImageUrl)
                                .toList();
  }
}

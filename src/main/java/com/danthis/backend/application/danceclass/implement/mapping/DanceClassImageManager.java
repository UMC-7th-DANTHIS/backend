package com.danthis.backend.application.danceclass.implement.mapping;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.danceclass.danceclassimage.repository.DanceClassImageRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassImageManager {

  private final DanceClassImageRepository danceClassImageRepository;

  @Transactional
  public void updateImages(DanceClass danceClass, Set<String> imageUrls) {
    danceClassImageRepository.deleteByDanceClassId(danceClass.getId());

    List<DanceClassImage> newImages = imageUrls.stream()
                                               .map(url -> DanceClassImage.builder()
                                                   .danceClass(danceClass)
                                                   .imageUrl(url)
                                                   .build())
                                               .toList();

    danceClassImageRepository.saveAll(newImages);
  }
}

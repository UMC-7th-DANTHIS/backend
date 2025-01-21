package com.danthis.backend.application.danceclass.implement;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.danceclass.danceclassimage.repository.DanceClassImageRepository;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import com.danthis.backend.domain.mapping.danceclasshashtag.DanceClassHashtag;
import com.danthis.backend.domain.mapping.danceclasshashtag.repository.DanceClassHashtagRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassManager {

  private final DanceClassRepository danceClassRepository;
  private final DanceClassImageRepository danceClassImageRepository;
  private final DanceClassHashtagRepository danceClassHashtagRepository;

  public void saveDanceClass(DanceClass danceClass) {
    danceClassRepository.save(danceClass);
  }

  public void saveDanceClassImages(Set<DanceClassImage> images) {
    danceClassImageRepository.saveAll(images);
  }

  public void saveDanceClassHashtags(Set<DanceClassHashtag> hashtags) {
    danceClassHashtagRepository.saveAll(hashtags);
  }
}

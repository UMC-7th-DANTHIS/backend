package com.danthis.backend.application.danceclass.implement.mapping;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.hashtag.Hashtag;
import com.danthis.backend.domain.mapping.danceclasshashtag.DanceClassHashtag;
import com.danthis.backend.domain.mapping.danceclasshashtag.repository.DanceClassHashtagRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassHashtagManager {

  private final DanceClassHashtagRepository danceClassHashtagRepository;

  @Transactional
  public void updateHashtags(DanceClass danceClass, Set<Hashtag> hashtags) {
    danceClassHashtagRepository.deleteByDanceClassId(danceClass.getId());

    List<DanceClassHashtag> newHashtags = hashtags.stream()
                                                  .map(hashtag -> DanceClassHashtag.builder()
                                                                                   .danceClass(danceClass)
                                                                                   .hashtag(hashtag)
                                                                                   .build())
                                                  .toList();

    danceClassHashtagRepository.saveAll(newHashtags);
  }
}

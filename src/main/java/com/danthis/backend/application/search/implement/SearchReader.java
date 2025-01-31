package com.danthis.backend.application.search.implement;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchReader {

  private final DanceClassRepository danceClassRepository;

  public Page<DanceClass> searchDanceClasses(String query, PageRequest pageable) {
    return danceClassRepository.searchByClassName(query, pageable);
  }
}

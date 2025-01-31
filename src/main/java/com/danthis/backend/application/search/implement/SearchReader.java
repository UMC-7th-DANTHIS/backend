package com.danthis.backend.application.search.implement;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchReader {

  private final DanceClassRepository danceClassRepository;
  private final DancerRepository dancerRepository;

  public Page<DanceClass> searchDanceClasses(String query, PageRequest pageable) {
    return danceClassRepository.searchByClassName(query, pageable);
  }

  public Page<Dancer> searchDancers(String query, PageRequest pageable) {
    return dancerRepository.searchByDancerName(query, pageable);
  }
}

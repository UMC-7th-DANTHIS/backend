package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerManager {

  private final DancerRepository dancerRepository;

  public void savedDancer(Dancer dancer) {
    dancerRepository.save(dancer);
  }
}

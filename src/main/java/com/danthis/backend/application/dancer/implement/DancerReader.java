package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerReader {

  private final DancerRepository dancerRepository;

  public Dancer readDancerById(Long dancerId) {
    return dancerRepository.findById(dancerId)
                           .orElseThrow(() -> new BusinessException(ErrorCode.DANCER_NOT_FOUND));
  }

  public List<Dancer> readByDancerGenre(List<DancerGenre> dancerGenreList) {
    return dancerGenreList.stream()
                          .map(DancerGenre::getDancer)
                          .toList();
  }

  public Dancer readDancerByUserId(Long userId) {
    return dancerRepository.findByUserId(userId).orElse(null);
  }
}

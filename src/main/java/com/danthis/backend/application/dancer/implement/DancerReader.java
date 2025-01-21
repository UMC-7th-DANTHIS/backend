package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.application.dancer.response.DancerSummaryResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerReader {

  private final DancerRepository dancerRepository;

  public Dancer readById(Long dancerId) {
    return dancerRepository.findById(dancerId)
                           .orElseThrow(() -> new BusinessException(ErrorCode.DANCER_NOT_FOUND));
  }

  public List<Dancer> readByDancerGenre(List<DancerGenre> dancerGenreList) {
    return dancerGenreList.stream()
                          .map(DancerGenre::getDancer)
                          .toList();
  }

  public List<DancerSummaryResponse> readDancersByDancerGenre(List<DancerGenre> dancerGenreList) {
    List<Dancer> dancers = dancerGenreList.stream()
                                          .map(DancerGenre::getDancer)  // Extract dancerId
                                          .toList();

    return dancers.stream()
                  .map(dancer -> DancerSummaryResponse.builder()
                                                      .id(dancer.getId())
                                                      .dancerName(dancer.getDancerName())
                                                      .imageUrlList(
                                                          dancer.getDancerImages().stream()
                                                                .map(DancerImage::getImageUrl)
                                                                .collect(Collectors.toSet()))
                                                      .build())
                  .collect(Collectors.toList());
  }
}

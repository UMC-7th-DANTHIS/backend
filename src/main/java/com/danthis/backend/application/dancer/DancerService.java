package com.danthis.backend.application.dancer;

import com.danthis.backend.application.dancer.implement.DancerManager;
import com.danthis.backend.application.dancer.implement.DancerMapper;
import com.danthis.backend.application.dancer.request.DancerAddServiceRequest;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.genre.Genre;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DancerService {

  private final DancerManager dancerManager;
  private final DancerMapper dancerMapper;

  @Transactional
  public void addDancerInfo(DancerAddServiceRequest request) {

    Set<DancerImage> images = dancerMapper.mapToDancerImage(request.getDancerImages());
    Set<Genre> genres = dancerMapper.mapToDancerGenre(request.getPreferredGenres());
    Dancer dancer = request.toDancer(images, genres);

    dancerManager.savedDancer(request.toDancer());
  }
}

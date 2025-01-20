package com.danthis.backend.application.dancer;

import com.danthis.backend.application.dancer.implement.DancerManager;
import com.danthis.backend.application.dancer.implement.DancerMapper;
import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.dancer.request.DancerAddServiceRequest;
import com.danthis.backend.application.dancer.request.DancerUpdateServiceRequest;
import com.danthis.backend.application.dancer.response.DancerInfoResponse;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
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
  private final DancerReader dancerReader;
  private final DancerMapper dancerMapper;
  private final UserReader userReader;

  @Transactional
  public Long addDancerInfo(Long userId, DancerAddServiceRequest request) {
    Dancer dancer = dancerManager.createDancer(userReader.readUserById(userId), request);

    Set<Genre> genres = dancerMapper.mapToDancerGenre(request.getPreferredGenres());
    Set<DancerImage> images = dancerMapper.mapToDancerImage(request.getDancerImages());

    Set<DancerGenre> dancerGenres = DancerGenre.createFromIds(dancer, genres);

    dancer.updateDancerGenres(dancerGenres);
    // Todo: 이미지 처리 필요

    dancerManager.savedDancer(dancer);
    return dancer.getId();
  }

  @Transactional
  public Long updateDancerInfo(DancerUpdateServiceRequest request) {
    Dancer dancer = dancerReader.readDancerById(request.getId());

    Set<Genre> genres = dancerMapper.mapToDancerGenre(request.getPreferredGenres());

    Set<DancerGenre> dancerGenres = DancerGenre.createFromIds(dancer, genres);

    dancer.updateDancerName(request.getDancerName());
    dancer.updateInstargramId(request.getInstargramId());
    dancer.updateBio(request.getBio());
    dancer.updateHistory(request.getHistory());
    dancer.updateOpenChatUrl(request.getOpenChatUrl());
    dancer.updateDancerGenres(dancerGenres);
    // Todo: 이미지 처리 필요

    dancerManager.savedDancer(dancer);
    return dancer.getId();
  }

  @Transactional
  public DancerInfoResponse getDancerInfo(Long dancerId) {
    Dancer dancer = dancerReader.readDancerById(dancerId);

    return DancerInfoResponse.builder()
                             .id(dancer.getId())
                             .dancerName(dancer.getDancerName())
                             .bio(dancer.getBio())
                             .history(dancer.getHistory())
                             .openChatUrl(dancer.getOpenChatUrl())
                             .favoriteGenres(dancer.getDancerGenres().stream()
                                                   .map(dancerGenre -> dancerGenre.getGenre()
                                                                                  .getId())
                                                   .toList())
                             // Todo: 이미지 처리 필요
                             .build();
  }
}

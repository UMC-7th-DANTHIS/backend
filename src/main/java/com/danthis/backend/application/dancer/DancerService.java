package com.danthis.backend.application.dancer;

import com.danthis.backend.application.dancer.implement.DancerManager;
import com.danthis.backend.application.dancer.implement.DancerMapper;
import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.dancer.implement.mapping.DancerGenreManager;
import com.danthis.backend.application.dancer.implement.mapping.DancerGenreReader;
import com.danthis.backend.application.dancer.implement.mapping.DancerImageManager;
import com.danthis.backend.application.dancer.implement.mapping.DancerImageReader;
import com.danthis.backend.application.dancer.request.DancerAddServiceRequest;
import com.danthis.backend.application.dancer.request.DancerUpdateServiceRequest;
import com.danthis.backend.application.dancer.response.DancerInfoResponse;
import com.danthis.backend.application.dancer.response.DancerSummaryListResponse;
import com.danthis.backend.application.dancer.response.DancerSummaryResponse;
import com.danthis.backend.application.dancer.response.PaginationInfo;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DancerService {

  private final DancerManager dancerManager;
  private final DancerReader dancerReader;
  private final DancerMapper dancerMapper;
  private final UserReader userReader;
  private final DancerGenreReader dancerGenreReader;
  private final DancerGenreManager dancerGenreManager;
  private final DancerImageManager dancerImageManager;
  private final DancerImageReader dancerImageReader;

  @Transactional
  public Long addDancerInfo(Long userId, DancerAddServiceRequest request) {
    Dancer dancer = dancerManager.createDancer(userReader.readUserById(userId), request);
    dancerManager.saveDancer(dancer);

    Set<Genre> genres = dancerMapper.mapToGenre(request.getPreferredGenres());
    Set<DancerGenre> dancerGenres = dancerMapper.mapToDancerGenre(dancer, genres);
    Set<DancerImage> dancerImages = dancerMapper.mapToDancerImage(dancer,
        request.getDancerImages());

    dancerGenreManager.saveAll(dancerGenres);
    dancerImageManager.saveAll(dancerImages);

    return dancer.getId();
  }

  @Transactional
  public Long updateDancerInfo(DancerUpdateServiceRequest request) {
    Dancer dancer = dancerReader.readDancerById(request.getId());

    dancer.updateDancerName(request.getDancerName());
    dancer.updateInstargramId(request.getInstargramId());
    dancer.updateBio(request.getBio());
    dancer.updateHistory(request.getHistory());
    dancer.updateOpenChatUrl(request.getOpenChatUrl());

    Set<Genre> genres = dancerMapper.mapToGenre(request.getPreferredGenres());
    Set<DancerGenre> dancerGenres = dancerMapper.mapToDancerGenre(dancer, genres);
    Set<DancerImage> dancerImages = dancerMapper.mapToDancerImage(dancer,
        request.getDancerImages());

    dancerGenreManager.deleteByDancer(dancer);
    dancerGenreManager.saveAll(dancerGenres);
    dancerImageManager.deleteByDancer(dancer);
    dancerImageManager.saveAll(dancerImages);

    dancer.updateDancerGenres(dancerGenres);
    dancer.updateDancerImages(dancerImages);

    dancerManager.saveDancer(dancer);
    return dancer.getId();
  }

  @Transactional
  public DancerInfoResponse getDancerInfo(Long dancerId) {
    Dancer dancer = dancerReader.readDancerById(dancerId);
    return DancerInfoResponse.builder()
                             .id(dancer.getId())
                             .dancerName(dancer.getDancerName())
                             .instargramId(dancer.getInstargramId())
                             .bio(dancer.getBio())
                             .history(dancer.getHistory())
                             .openChatUrl(dancer.getOpenChatUrl())
                             .favoriteGenres(dancerGenreReader.findGenreIdByDancer(dancer))
                             .imageUrlList(dancerImageReader.findImageUrlByDancer(dancer))
                             .build();
  }

  @Transactional
  public DancerSummaryListResponse getDancersByGenre(Long genreId, Integer page, Integer size) {
    Page<DancerGenre> pages = dancerGenreReader.readDancerGenresByGenreId(genreId, page, size);

    List<Dancer> candidates = dancerReader.readByDancerGenre(pages.getContent());
    List<DancerSummaryResponse> dancerInfos = dancerManager.toSummaryInfo(candidates);
    PaginationInfo pagination = dancerManager.createPagination(
        pages.getNumber(),
        pages.getTotalPages());

    return dancerManager.createDancerSummaryListResponse(dancerInfos, pagination);
  }
}

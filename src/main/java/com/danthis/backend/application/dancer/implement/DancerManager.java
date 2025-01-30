package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.application.dancer.request.DancerAddServiceRequest;
import com.danthis.backend.application.dancer.response.DancerSummaryListResponse;
import com.danthis.backend.application.dancer.response.DancerSummaryResponse;
import com.danthis.backend.application.dancer.response.PaginationInfo;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.dancerimage.repository.DancerImageRepository;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.dancergenre.repository.DancerGenreRepository;
import com.danthis.backend.domain.user.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerManager {

  private final DancerRepository dancerRepository;
  private final DancerGenreRepository dancerGenreRepository;
  private final DancerImageRepository dancerImageRepository;

  public Dancer createDancer(User user, DancerAddServiceRequest request) {
    Boolean permission = true;  // 모든 댄서 가입이 바로 허용된다. 나중에 false로 변경해야 함
    return Dancer.builder()
                 .user(user)
                 .dancerName(request.getDancerName())
                 .instargramId(request.getInstargramId())
                 .bio(request.getBio())
                 .history(request.getHistory())
                 .isApproved(permission)
                 .openChatUrl(request.getOpenChatUrl())
                 .build();
  }

  public void saveDancer(Dancer dancer) {
    dancerRepository.save(dancer);
  }

  public void saveDancerGenre(Set<DancerGenre> dancerGenres) {
    dancerGenreRepository.saveAll(dancerGenres);
  }

  public void saveDancerImage(Set<DancerImage> dancerImages) {
    dancerImageRepository.saveAll(dancerImages);
  }

  public List<DancerSummaryResponse> toSummaryInfo(List<Dancer> dancers) {
    return dancers.stream()
                  .map(dancer -> DancerSummaryResponse.builder()
                                                      .id(dancer.getId())
                                                      .dancerName(dancer.getDancerName())
                                                      .imageUrlList(
                                                          dancer.getDancerImages().stream()
                                                                .map(DancerImage::getImageUrl)
                                                                .collect(Collectors.toSet()))
                                                      .build())
                  .toList();
  }

  public PaginationInfo createPagination(Integer number, Integer totalPages) {
    return PaginationInfo.builder()
                         .currentPage(number)
                         .totalPages(totalPages)
                         .build();
  }

  public DancerSummaryListResponse createDancerSummaryListResponse(
      List<DancerSummaryResponse> dancers,
      PaginationInfo pagination) {
    return DancerSummaryListResponse.builder()
                                    .dancers(dancers)
                                    .pagination(pagination)
                                    .build();
  }
}

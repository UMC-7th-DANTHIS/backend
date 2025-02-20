package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.application.dancer.request.DancerAddServiceRequest;
import com.danthis.backend.application.dancer.response.DancerSummaryListResponse;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.user.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerManager {

  private final DancerRepository dancerRepository;

  public Dancer createDancer(User user, DancerAddServiceRequest request) {
    Boolean permission = true;  // 모든 댄서 가입이 바로 허용된다. 나중에 false 로 변경해야 함
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

  public List<DancerSummaryListResponse.DancerSummaryResponse> toSummaryInfo(List<Dancer> dancers) {
    return dancers.stream()
                  .map(dancer -> DancerSummaryListResponse.DancerSummaryResponse.builder()
                                                                                .id(dancer.getId())
                                                                                .dancerName(dancer.getDancerName())
                                                                                .images(
                                                                                    dancer.getDancerImages().stream()
                                                                                          .map(DancerImage::getImageUrl)
                                                                                          .collect(Collectors.toSet()))
                                                                                .genres(
                                                                                    dancer.getGenres().stream()
                                                                                          .map(Genre::getName)
                                                                                          .collect(Collectors.toSet()))
                                                                                .build())
                  .toList();
  }
}

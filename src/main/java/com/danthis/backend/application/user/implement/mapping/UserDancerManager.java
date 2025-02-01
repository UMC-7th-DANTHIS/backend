package com.danthis.backend.application.user.implement.mapping;

import com.danthis.backend.application.user.response.UserFavoriteResponse.DancerInfo;
import com.danthis.backend.application.user.response.UserFavoriteResponse.FavoriteDancerListResponse;
import com.danthis.backend.application.user.response.UserFavoriteResponse.Pagination;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.userdancer.repository.UserDancerRepository;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDancerManager {

  private final UserDancerRepository userDancerRepository;

  @Transactional
  public void saveAll(Set<UserDancer> userDancers) {
    userDancerRepository.saveAll(userDancers);
  }

  @Transactional
  public void deleteByUser(User user) {
    userDancerRepository.deleteByUser(user.getId());
  }

  public FavoriteDancerListResponse toFavoriteDancerListInfo(Page<UserDancer> userDancerPage) {
    List<DancerInfo> dancerInfoList = userDancerPage.getContent().stream().map(
        userDancer -> DancerInfo.builder()
                                .id(userDancer.getDancer().getId())
                                .dancerName(userDancer.getDancer().getDancerName())
                                .images(userDancer.getDancer().getDancerImages().stream()
                                                  .map(DancerImage::getImageUrl)
                                                  .toList())
                                .build()).toList();

    Pagination pagination = Pagination.builder()
                                      .currentPage(userDancerPage.getNumber())
                                      .totalPages(userDancerPage.getTotalPages())
                                      .build();

    return FavoriteDancerListResponse.builder()
                                     .dancers(dancerInfoList)
                                     .pagination(pagination)
                                     .build();
  }
}

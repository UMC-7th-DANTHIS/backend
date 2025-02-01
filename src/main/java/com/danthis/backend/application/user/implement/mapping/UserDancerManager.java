package com.danthis.backend.application.user.implement.mapping;

import com.danthis.backend.application.user.response.UserFavoriteResponse.DancerInfo;
import com.danthis.backend.application.user.response.UserFavoriteResponse.FavoriteDancerListResponse;
import com.danthis.backend.application.user.response.UserFavoriteResponse.Pagination;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.Dancer;
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

  public UserDancer toUserDancer(User user, Dancer dancer) {
    return UserDancer.builder()
                     .user(user)
                     .dancer(dancer)
                     .build();
  }

  @Transactional
  public void saveUserDancer(UserDancer userDancer) {
    userDancerRepository.save(userDancer);
  }

  @Transactional
  public void deleteUserDancer(UserDancer userDancer) {
    userDancerRepository.delete(userDancer);
  }
}

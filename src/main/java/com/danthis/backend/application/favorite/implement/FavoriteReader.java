package com.danthis.backend.application.favorite.implement;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.userdancer.repository.UserDancerRepository;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import com.danthis.backend.domain.mapping.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteReader {

  private final UserDancerRepository userDancerRepository;
  private final WishListRepository wishListRepository;

  public Page<UserDancer> readDancersByUserId(Long userId, Pageable pageable) {
    return userDancerRepository.findByUserId(userId, pageable);
  }

  public Page<WishList> readWishListByUserId(Long userId, Pageable pageable) {
    return wishListRepository.findByUserId(userId, pageable);
  }
}

package com.danthis.backend.application.user.implement.mapping;

import com.danthis.backend.domain.mapping.wishlist.WishList;
import com.danthis.backend.domain.mapping.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishListReader {

  private final WishListRepository wishListRepository;

  public Page<WishList> readWishListByUserId(Long userId, Pageable pageable) {
    return wishListRepository.findByUserId(userId, pageable);
  }
}

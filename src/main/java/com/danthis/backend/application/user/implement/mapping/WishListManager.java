package com.danthis.backend.application.user.implement.mapping;

import com.danthis.backend.domain.mapping.wishlist.WishList;
import com.danthis.backend.domain.mapping.wishlist.repository.WishListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishListManager {

  private final WishListRepository wishListRepository;

  @Transactional
  public void saveWishList(WishList wishList) {
    wishListRepository.save(wishList);
  }

  @Transactional
  public void deleteWishList(WishList wishList) {
    wishListRepository.delete(wishList);
  }
}

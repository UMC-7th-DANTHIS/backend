package com.danthis.backend.application.favorite;

import com.danthis.backend.application.favorite.implement.FavoriteManager;
import com.danthis.backend.application.favorite.implement.FavoriteReader;
import com.danthis.backend.application.favorite.response.FavoriteInfoResponse.FavoriteDancerListInfo;
import com.danthis.backend.application.favorite.response.FavoriteInfoResponse.WishListInfo;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteReader favoriteReader;
  private final FavoriteManager favoriteManager;

  @Transactional
  public FavoriteDancerListInfo getFavoriteDancers(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<UserDancer> dancers = favoriteReader.readDancersByUserId(userId, pageable);
    return favoriteManager.toFavoriteDancerListInfo(dancers);
  }

  @Transactional
  public WishListInfo getWishList(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<WishList> wishLists = favoriteReader.readWishListByUserId(userId, pageable);
    return favoriteManager.toWishListInfo(wishLists);
  }
}

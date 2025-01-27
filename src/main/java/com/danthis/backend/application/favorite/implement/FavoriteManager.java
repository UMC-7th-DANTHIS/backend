package com.danthis.backend.application.favorite.implement;

import com.danthis.backend.application.favorite.response.FavoriteInfoResponse.DanceClassInfo;
import com.danthis.backend.application.favorite.response.FavoriteInfoResponse.DancerInfo;
import com.danthis.backend.application.favorite.response.FavoriteInfoResponse.FavoriteDancerListInfo;
import com.danthis.backend.application.favorite.response.FavoriteInfoResponse.Pagination;
import com.danthis.backend.application.favorite.response.FavoriteInfoResponse.WishListInfo;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteManager {

  public FavoriteDancerListInfo toFavoriteDancerListInfo(Page<UserDancer> userDancerPage) {
    List<DancerInfo> dancerInfoList = userDancerPage.getContent().stream().map(
        userDancer -> DancerInfo.builder()
                                .id(userDancer.getDancer().getId())
                                .dancerName(userDancer.getDancer().getDancerName())
                                .imageUrlList(userDancer.getDancer().getDancerImages().stream()
                                                        .map(DancerImage::getImageUrl)
                                                        .collect(Collectors.toList()))
                                .build()).toList();

    Pagination pagination = Pagination.builder()
                                      .currentPage(userDancerPage.getNumber())
                                      .totalPages(userDancerPage.getTotalPages())
                                      .build();

    return FavoriteDancerListInfo.builder()
                                 .dancers(dancerInfoList)
                                 .pagination(pagination)
                                 .build();
  }

  public WishListInfo toWishListInfo(Page<WishList> wishListPage) {
    List<DanceClassInfo> classInfoList = wishListPage.getContent().stream().map(
        wishList -> DanceClassInfo.builder()
                                  .id(wishList.getDanceClass().getId())
                                  .className(wishList.getDanceClass().getClassName())
                                  .dancerName(wishList.getDanceClass().getDancer().getDancerName())
                                  .images(wishList.getDanceClass().getDanceClassImages().stream()
                                                  .map(DanceClassImage::getImageUrl)
                                                  .collect(Collectors.toList()))
                                  .build()).toList();

    Pagination pagination = Pagination.builder()
                                      .currentPage(wishListPage.getNumber())
                                      .totalPages(wishListPage.getTotalPages())
                                      .build();

    return WishListInfo.builder()
                       .danceClasses(classInfoList)
                       .pagination(pagination)
                       .build();
  }
}

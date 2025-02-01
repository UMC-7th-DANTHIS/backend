package com.danthis.backend.application.user.implement.mapping;

import com.danthis.backend.application.user.response.UserFavoriteResponse.DanceClassInfo;
import com.danthis.backend.application.user.response.UserFavoriteResponse.Pagination;
import com.danthis.backend.application.user.response.UserFavoriteResponse.WishListResponse;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishListManager {

  public WishListResponse toWishListInfo(Page<WishList> wishListPage) {
    List<DanceClassInfo> classInfoList = wishListPage.getContent().stream().map(
        wishList -> DanceClassInfo.builder()
                                  .id(wishList.getDanceClass().getId())
                                  .className(wishList.getDanceClass().getClassName())
                                  .dancerName(wishList.getDanceClass().getDancer().getDancerName())
                                  .images(wishList.getDanceClass().getDanceClassImages().stream()
                                                  .map(DanceClassImage::getImageUrl)
                                                  .toList())
                                  .build()).toList();

    Pagination pagination = Pagination.builder()
                                      .currentPage(wishListPage.getNumber())
                                      .totalPages(wishListPage.getTotalPages())
                                      .build();

    return WishListResponse.builder()
                           .danceClasses(classInfoList)
                           .pagination(pagination)
                           .build();
  }
}

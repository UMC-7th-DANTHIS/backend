package com.danthis.backend.application.user.response;

import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

public class UserFavoriteResponse {

  @Getter
  @Builder
  public static class FavoriteDancerListResponse {

    List<DancerInfo> dancers;
    Pagination pagination;

    public static FavoriteDancerListResponse from(Page<UserDancer> userDancerPage) {
      List<DancerInfo> dancerInfoList = userDancerPage.getContent().stream()
                                                      .map(userDancer -> DancerInfo.builder()
                                                                                   .id(userDancer.getDancer().getId())
                                                                                   .dancerName(userDancer.getDancer().getDancerName())
                                                                                   .images(userDancer.getDancer().getDancerImages().stream()
                                                                                                     .map(DancerImage::getImageUrl)
                                                                                                     .toList())
                                                                                   .build())
                                                      .toList();

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

  @Getter
  @Builder
  public static class DancerInfo {

    private Long id;
    private String dancerName;
    private List<String> images;
  }

  @Getter
  @Builder
  public static class WishListResponse {

    List<DanceClassInfo> danceClasses;
    Pagination pagination;

    public static WishListResponse from(Page<WishList> wishListPage) {
      List<DanceClassInfo> classInfoList = wishListPage.getContent().stream().map(
          wishList -> DanceClassInfo.builder()
                                    .id(wishList.getDanceClass().getId())
                                    .className(wishList.getDanceClass().getClassName())
                                    .dancerName(wishList.getDanceClass().getDancer().getDancerName())
                                    .images(wishList.getDanceClass().getDanceClassImages()
                                                    .stream()
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

  @Getter
  @Builder
  public static class DanceClassInfo {

    private Long id;
    private String className;
    private String dancerName;
    private List<String> images;
  }

  @Getter
  @Builder
  public static class Pagination {

    private Integer currentPage;
    private Integer totalPages;
  }
}

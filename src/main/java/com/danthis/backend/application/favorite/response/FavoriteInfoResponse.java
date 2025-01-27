package com.danthis.backend.application.favorite.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteInfoResponse {

  @Getter
  @Builder
  public static class FavoriteDancerListInfo {

    List<DancerInfo> dancers;
    Pagination pagination;
  }

  @Getter
  @Builder
  public static class DancerInfo {

    private Long id;
    private String dancerName;
    private List<String> imageUrlList;
  }

  @Getter
  @Builder
  public static class WishListInfo {

    List<DanceClassInfo> danceClasses;
    Pagination pagination;
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

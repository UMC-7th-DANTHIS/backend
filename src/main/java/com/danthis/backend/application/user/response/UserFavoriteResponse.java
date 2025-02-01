package com.danthis.backend.application.user.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class UserFavoriteResponse {

  @Getter
  @Builder
  public static class FavoriteDancerListResponse {

    List<DancerInfo> dancers;
    Pagination pagination;
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

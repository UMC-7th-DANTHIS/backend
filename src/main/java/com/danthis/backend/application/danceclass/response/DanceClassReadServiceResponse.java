package com.danthis.backend.application.danceclass.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DanceClassReadServiceResponse {

  private Long id;
  private String className;
  private Dancer dancer;
  private Long genre;
  private Integer pricePerSession;
  private Integer difficulty;
  private Details details;
  private List<ClassReview> classReviews;
  private Pagination pagination;

  @Getter
  @Builder
  public static class Dancer {

    private String name;
    private String profileImage;
    private String openChatUrl;
  }

  @Getter
  @Builder
  public static class Details {

    private String videoUrl;
    private String description;
    private String targetAudience;
    private List<Long> hashtags;
    private List<String> danceClassImages;
  }

  @Getter
  @Builder
  public static class ClassReview {

    private Long id;
    private String author;
    private String title;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
    private List<String> reviewImages;
  }

  @Getter
  @Builder
  public static class Pagination {

    private int currentPage;
    private int totalPages;
    private int totalReviews;
  }
}

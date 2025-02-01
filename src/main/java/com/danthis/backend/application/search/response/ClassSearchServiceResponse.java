package com.danthis.backend.application.search.response;

import com.danthis.backend.domain.danceclass.DanceClass;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class ClassSearchServiceResponse {

  private String query;
  private List<DanceClassSummary> results;
  private Pagination pagination;

  @Getter
  @Builder
  public static class DanceClassSummary {

    private Long id;
    private String className;
    private String dancer;
    private Long genre;
    private Integer pricePerSession;
    private Integer difficulty;
    private List<String> classImage;
  }

  @Getter
  @Builder
  public static class Pagination {

    private int currentPage;
    private int totalPages;
    private long totalResults;
  }

  public static ClassSearchServiceResponse from(String query, Page<DanceClass> danceClassPage) {
    List<DanceClassSummary> danceClassSummaries = danceClassPage.getContent().stream()
                                                                .map(
                                                                    danceClass -> DanceClassSummary.builder()
                                                                                                   .id(danceClass.getId())
                                                                                                   .className(danceClass.getClassName())
                                                                                                   .dancer(danceClass.getDancer().getDancerName())
                                                                                                   .genre(danceClass.getGenre().getId())
                                                                                                   .pricePerSession(danceClass.getPricePerSession())
                                                                                                   .difficulty(danceClass.getDifficulty())
                                                                                                   .classImage(danceClass.getDanceClassImages()
                                                                                                                 .stream()
                                                                                                                 .map(image -> image.getImageUrl())
                                                                                                                 .toList())
                                                                                                   .build())
                                                                .toList();

    Pagination paginationInfo = Pagination.builder()
                                          .currentPage(danceClassPage.getNumber() + 1)
                                          .totalPages(danceClassPage.getTotalPages())
                                          .totalResults(danceClassPage.getTotalElements())
                                          .build();

    return ClassSearchServiceResponse.builder()
                                     .query(query)
                                     .results(danceClassSummaries)
                                     .pagination(paginationInfo)
                                     .build();
  }
}

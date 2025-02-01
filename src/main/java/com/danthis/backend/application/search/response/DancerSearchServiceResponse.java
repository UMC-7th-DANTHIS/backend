package com.danthis.backend.application.search.response;

import com.danthis.backend.domain.dancer.Dancer;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class DancerSearchServiceResponse {

  private String query;
  private List<DancerSummary> results;
  private Pagination pagination;

  @Getter
  @Builder
  public static class DancerSummary {

    private Long id;
    private String name;
    private List<String> profileImage;
    private String instagramId;
    private List<Long> mainGenres;
  }

  @Getter
  @Builder
  public static class Pagination {

    private int currentPage;
    private int totalPages;
    private long totalResults;
  }

  public static DancerSearchServiceResponse from(String query, Page<Dancer> dancerPage) {
    List<DancerSummary> dancerSummaries = dancerPage.getContent().stream()
                                                    .map(dancer -> DancerSummary.builder()
                                                                                .id(dancer.getId())
                                                                                .name(dancer.getDancerName())
                                                                                .profileImage(dancer.getDancerImages()
                                                                                          .stream()
                                                                                          .map(
                                                                                              dancerImage -> dancerImage.getImageUrl())
                                                                                          .toList())
                                                                                .instagramId(dancer.getInstargramId())
                                                                                .mainGenres(dancer.getDancerGenres()
                                                                                          .stream()
                                                                                          .map(
                                                                                              dancerGenre -> dancerGenre.getGenre()
                                                                                                                        .getId())
                                                                                          .toList())
                                                                                .build())
                                                    .toList();

    Pagination paginationInfo = Pagination.builder()
                                          .currentPage(dancerPage.getNumber() + 1)
                                          .totalPages(dancerPage.getTotalPages())
                                          .totalResults(dancerPage.getTotalElements())
                                          .build();

    return DancerSearchServiceResponse.builder()
                                      .query(query)
                                      .results(dancerSummaries)
                                      .pagination(paginationInfo)
                                      .build();
  }
}

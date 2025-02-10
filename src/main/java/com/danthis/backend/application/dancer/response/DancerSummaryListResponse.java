package com.danthis.backend.application.dancer.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerSummaryListResponse {

  private List<DancerSummaryResponse> dancers;
  private Integer currentPage;
  private Integer totalPages;
  private Long totalElements;

  public static DancerSummaryListResponse from(final List<DancerSummaryResponse> dancers,
      final Integer currentPage, final Integer totalPages, final Long totalElements) {
    return DancerSummaryListResponse.builder()
                                    .dancers(dancers)
                                    .currentPage(currentPage + 1)
                                    .totalPages(totalPages)
                                    .totalElements(totalElements)
                                    .build();
  }
}

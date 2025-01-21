package com.danthis.backend.application.dancer.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaginationInfo {

  private Integer currentPage;
  private Integer totalPages;
  private Long totalResults;
}

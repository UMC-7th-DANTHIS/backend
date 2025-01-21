package com.danthis.backend.application.dancer.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerSummaryListResponse {

  private List<DancerSummaryResponse> dancers;
  private PaginationInfo pagination;
}

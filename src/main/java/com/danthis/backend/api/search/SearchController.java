package com.danthis.backend.api.search;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.application.search.SearchService;
import com.danthis.backend.application.search.response.ClassSearchServiceResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
@RequiredArgsConstructor
@Tag(name = "검색", description = "검색 기능 관련 API")
public class SearchController {

  private final SearchService searchService;

  @Operation(summary = "댄스 수업 검색 API", description = "검색어를 포함한 댄스 수업 목록을 조회합니다.")
  @GetMapping("/dance-classes")
  public ApiResponse<ClassSearchServiceResponse> searchDanceClasses(
      @RequestParam String query,
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "5") @Min(1) int size
  ) {
    if (query.trim().isEmpty()) {
      throw new BusinessException(ErrorCode.INVALID_SEARCH_QUERY);
    }

    ClassSearchServiceResponse response = searchService.searchDanceClasses(query, page, size);
    return ApiResponse.OK(response);
  }
}

package com.danthis.backend.application.search;

import com.danthis.backend.application.search.implement.SearchReader;
import com.danthis.backend.application.search.response.ClassSearchServiceResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.danceclass.DanceClass;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

  private final SearchReader searchReader;

  @Transactional
  public ClassSearchServiceResponse searchDanceClasses(String query, int page, int size) {
    if (query == null || query.trim().isEmpty()) {
      throw new BusinessException(ErrorCode.INVALID_SEARCH_QUERY);
    }

    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<DanceClass> danceClassPage = searchReader.searchDanceClasses(query, pageable);

    return ClassSearchServiceResponse.from(query, danceClassPage);
  }
}

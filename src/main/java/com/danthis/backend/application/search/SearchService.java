package com.danthis.backend.application.search;

import com.danthis.backend.application.search.implement.SearchReader;
import com.danthis.backend.application.search.response.ClassSearchServiceResponse;
import com.danthis.backend.application.search.response.CommunityPostSearchServiceResponse;
import com.danthis.backend.application.search.response.DancerSearchServiceResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.dancer.Dancer;
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

  @Transactional
  public DancerSearchServiceResponse searchDancers(String query, int page, int size) {
    if (query == null || query.trim().isEmpty()) {
      throw new BusinessException(ErrorCode.INVALID_SEARCH_QUERY);
    }

    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<Dancer> dancerPage = searchReader.searchDancers(query, pageable);

    return DancerSearchServiceResponse.from(query, dancerPage);
  }

  @Transactional
  public CommunityPostSearchServiceResponse searchCommunityPosts(String query, int page, int size) {
    if (query == null || query.trim().isEmpty()) {
      throw new BusinessException(ErrorCode.INVALID_SEARCH_QUERY);
    }

    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<CommunityPost> postPage = searchReader.searchCommunityPosts(query, pageable);

    return CommunityPostSearchServiceResponse.from(query, postPage);
  }
}

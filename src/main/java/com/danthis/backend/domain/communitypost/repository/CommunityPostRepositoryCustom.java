package com.danthis.backend.domain.communitypost.repository;

import com.danthis.backend.domain.communitypost.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityPostRepositoryCustom {

  Page<CommunityPost> findByUserId(Long userId, Pageable pageable);
  Page<CommunityPost> searchByPostTitle(String query, Pageable pageable);
}

package com.danthis.backend.domain.communitycomment.repository;

import com.danthis.backend.domain.communitycomment.CommunityComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityCommentRepositoryCustom {

  Page<CommunityComment> findByUserId(Long userId, Pageable pageable);
}

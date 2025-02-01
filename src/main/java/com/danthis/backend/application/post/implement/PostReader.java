package com.danthis.backend.application.post.implement;

import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.repository.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReader {

  private final CommunityPostRepository communityPostRepository;

  public Page<CommunityPost> readPostsByUserId(Long userId, Pageable pageable) {
    return communityPostRepository.findByUserId(userId, pageable);
  }
}

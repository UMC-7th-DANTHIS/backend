package com.danthis.backend.application.comment.implement;

import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitycomment.repository.CommunityCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReader {

  private final CommunityCommentRepository communityCommentRepository;

  public Page<CommunityComment> readCommentsByUserId(Long userId, Pageable pageable) {
    return communityCommentRepository.findByUserId(userId, pageable);
  }
}

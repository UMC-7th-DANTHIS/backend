package com.danthis.backend.application.community.implement;

import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitycomment.repository.CommunityCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentManager {

  private final CommunityCommentRepository communityCommentRepository;

  public void saveComment(CommunityComment comment) {
    communityCommentRepository.save(comment);
  }

  public void deleteComment(CommunityComment comment) {
    communityCommentRepository.delete(comment);
  }
}

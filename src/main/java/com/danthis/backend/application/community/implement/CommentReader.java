package com.danthis.backend.application.community.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitycomment.repository.CommunityCommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReader {

  private final CommunityCommentRepository communityCommentRepository;

  public Page<CommunityComment> readCommentsByPostId(Long postId, int page, int size) {
    return communityCommentRepository.findByPostId(postId, PageRequest.of(page - 1, size));
  }

  public CommunityComment readCommentById(Long commentId) {
    return communityCommentRepository.findById(commentId)
                                     .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
  }

  public List<CommunityComment> readCommentsByPostId(Long postId) {
    return communityCommentRepository.findByPostId(postId);
  }
}

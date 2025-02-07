package com.danthis.backend.application.community.implement;

import com.danthis.backend.application.community.request.CommentCreateServiceRequest;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

  public CommunityComment mapToEntity(CommentCreateServiceRequest request, User user,
      CommunityPost post) {
    return CommunityComment.builder()
                           .content(request.getContent())
                           .user(user)
                           .post(post)
                           .build();
  }
}

package com.danthis.backend.application.community.implement;

import com.danthis.backend.application.community.request.PostCreateServiceRequest;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

  public CommunityPost mapToEntity(PostCreateServiceRequest request, User user) {
    return CommunityPost.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .views(0)
                        .user(user)
                        .build();
  }
}

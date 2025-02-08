package com.danthis.backend.application.community.implement;

import com.danthis.backend.application.community.request.PostCreateServiceRequest;
import com.danthis.backend.application.community.response.PostListServiceResponse;
import com.danthis.backend.application.community.response.PostReadServiceResponse;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.communitypostimage.CommunityPostImage;
import com.danthis.backend.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

  public PostReadServiceResponse toPostReadServiceResponse(CommunityPost post) {
    List<String> imageUrls = post.getCommunityPostImages().stream()
                                 .map(CommunityPostImage::getUrl)
                                 .toList();

    return PostReadServiceResponse.builder()
                                  .postId(post.getId())
                                  .title(post.getTitle())
                                  .author(post.getUser().getNickname())
                                  .createdAt(post.getCreatedAt())
                                  .content(post.getContent())
                                  .commentCount(post.getCommunityComments().size())
                                  .images(imageUrls)
                                  .build();
  }

  public PostListServiceResponse toPostListResponse(Page<CommunityPost> postPage) {
    List<PostListServiceResponse.PostSummary> posts = postPage.getContent().stream()
                                                              .map(post -> PostListServiceResponse.PostSummary.builder()
                                                                                                             .postId(post.getId())
                                                                                                             .title(post.getTitle())
                                                                                                             .createdAt(post.getCreatedAt())
                                                                                                             .commentCount(post.getCommunityComments().size())
                                                                                                             .build())
                                                              .toList();

    return PostListServiceResponse.builder()
                                  .currentPage(postPage.getNumber() + 1)
                                  .totalPages(postPage.getTotalPages())
                                  .totalPosts((int) postPage.getTotalElements())
                                  .posts(posts)
                                  .build();
  }
}

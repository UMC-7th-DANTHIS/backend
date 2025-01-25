package com.danthis.backend.application.post.implement;

import com.danthis.backend.application.user.response.UserCommunityResponse.PaginationDto;
import com.danthis.backend.application.user.response.UserCommunityResponse.PostDto;
import com.danthis.backend.application.user.response.UserCommunityResponse.UserPostsResponse;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.communitypostimage.CommunityPostImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostManager {

  public List<PostDto> toPostDtoList(List<CommunityPost> communityPosts) {
    return communityPosts.stream()
                         .map(communityPost -> PostDto.builder()
                                                      .title(communityPost.getTitle())
                                                      .content(communityPost.getContent())
                                                      .images(communityPost.getCommunityPostImages()
                                                                           .stream()
                                                                           .map(
                                                                               CommunityPostImage::getUrl)
                                                                           .toList())
                                                      .build())
                         .toList();
  }

  public PaginationDto toPagination(Integer currentPage, Integer totalPages) {
    return PaginationDto.builder()
                        .currentPage(currentPage)
                        .totalPages(totalPages)
                        .build();
  }

  public UserPostsResponse toUserPostsResponse(List<PostDto> postDtoList,
      PaginationDto pagination) {
    return UserPostsResponse.builder()
                            .posts(postDtoList)
                            .pagination(pagination)
                            .build();
  }
}

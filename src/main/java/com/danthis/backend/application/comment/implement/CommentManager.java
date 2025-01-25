package com.danthis.backend.application.comment.implement;

import com.danthis.backend.application.user.response.UserCommunityResponse.CommentDto;
import com.danthis.backend.application.user.response.UserCommunityResponse.PaginationDto;
import com.danthis.backend.application.user.response.UserCommunityResponse.UserCommentsResponse;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CommentManager {

  public List<CommentDto> toCommentDtoList(List<CommunityComment> communityComments) {
    return communityComments.stream()
                            .map(communityComment -> CommentDto.builder()
                                                               .postId(communityComment.getPost()
                                                                                       .getId())
                                                               .content(
                                                                   communityComment.getContent())
                                                               .build())
                            .toList();
  }

  public PaginationDto toPagination(Integer currentPage, Integer totalPages) {
    return PaginationDto.builder()
                        .currentPage(currentPage)
                        .totalPages(totalPages)
                        .build();
  }

  public UserCommentsResponse toUserCommentsResponse(List<CommentDto> commentDtoList,
      PaginationDto pagination) {
    return UserCommentsResponse.builder()
                               .comments(commentDtoList)
                               .pagination(pagination)
                               .build();
  }
}

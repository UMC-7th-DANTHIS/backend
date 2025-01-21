package com.danthis.backend.application.user;

import com.danthis.backend.application.comment.implement.CommentManager;
import com.danthis.backend.application.comment.implement.CommentReader;
import com.danthis.backend.application.post.implement.PostManager;
import com.danthis.backend.application.post.implement.PostReader;
import com.danthis.backend.application.user.implement.UserManager;
import com.danthis.backend.application.user.implement.UserPreferenceMapper;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.application.user.request.UserUpdateServiceRequest;
import com.danthis.backend.application.user.response.UserCommentsResponse;
import com.danthis.backend.application.user.response.UserCommentsResponse.CommentDto;
import com.danthis.backend.application.user.response.UserInfoResponse;
import com.danthis.backend.application.user.response.UserPostsResponse;
import com.danthis.backend.application.user.response.UserPostsResponse.PaginationDto;
import com.danthis.backend.application.user.response.UserPostsResponse.PostDto;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserReader userReader;
  private final UserManager userManager;
  private final UserPreferenceMapper userPreferenceMapper;
  private final PostReader postReader;
  private final PostManager postManager;
  private final CommentReader commentReader;
  private final CommentManager commentManager;

  @Transactional
  public void updateUserInfo(Long userId, UserUpdateServiceRequest request) {
    User user = userReader.readUserById(userId);

    user.updateNickname(request.getNickname());
    user.updateGender(request.getGender());
    user.updateEmail(request.getEmail());
    user.updatePhoneNumber(request.getPhoneNumber());
    user.updateProfileImage(request.getProfileImage());

    Set<Genre> genres = userPreferenceMapper.mapToGenres(request.getPreferredGenres());
    Set<Dancer> dancers = userPreferenceMapper.mapToDancers(request.getPreferredDancers());

    Set<UserGenre> updatedGenres = UserGenre.createFromIds(user, genres);
    Set<UserDancer> updatedDancers = UserDancer.createFromIds(user, dancers);

    user.updatePreferredGenres(updatedGenres);
    user.updatePreferredDancers(updatedDancers);

    userManager.saveUser(user);
  }

  @Transactional
  public boolean isNicknameAvailable(String nickname) {
    return userReader.isNicknameAvailable(nickname);
  }

  @Transactional
  public UserInfoResponse getUserInfo(Long userId) {
    User user = userReader.readUserById(userId);

    return UserInfoResponse.builder()
                           .nickname(user.getNickname())
                           .gender(user.getGender())
                           .email(user.getEmail())
                           .phoneNumber(user.getPhoneNumber())
                           .profileImage(user.getProfileImage())
                           .preferredGenres(user.getUserGenres().stream()
                                                .map(userGenre -> userGenre.getGenre().getId())
                                                .toList())
                           .preferredDancers(user.getUserDancers().stream()
                                                 .map(userDancer -> userDancer.getDancer().getId())
                                                 .toList())
                           .build();
  }

  @Transactional
  public UserPostsResponse getUserPosts(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<CommunityPost> posts = postReader.readPostsByUserId(userId, pageable);
    List<PostDto> postDtoList = postManager.toPostDtoList(posts.getContent());
    PaginationDto pagination = postManager.createPagination(
        posts.getNumber(),
        posts.getTotalPages()
    );

    return postManager.createUserPostsResponse(postDtoList, pagination);
  }

  @Transactional
  public UserCommentsResponse getUserComments(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<CommunityComment> comments = commentReader.readCommentsByUserId(userId, pageable);
    List<CommentDto> commentDtoList = commentManager.toCommentDtoList(comments.getContent());
    UserCommentsResponse.PaginationDto pagination = commentManager.createPagination(
        comments.getNumber(),
        comments.getTotalPages()
    );

    return commentManager.createUserCommentsResponse(commentDtoList, pagination);
  }
}

package com.danthis.backend.application.user;

import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.post.implement.PostManager;
import com.danthis.backend.application.post.implement.PostReader;
import com.danthis.backend.application.review.implement.ReviewManager;
import com.danthis.backend.application.review.implement.ReviewReader;
import com.danthis.backend.application.user.implement.UserManager;
import com.danthis.backend.application.user.implement.UserPreferenceMapper;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.application.user.implement.mapping.UserDancerManager;
import com.danthis.backend.application.user.implement.mapping.UserDancerReader;
import com.danthis.backend.application.user.implement.mapping.UserGenreManager;
import com.danthis.backend.application.user.implement.mapping.UserGenreReader;
import com.danthis.backend.application.user.implement.mapping.WishListManager;
import com.danthis.backend.application.user.implement.mapping.WishListReader;
import com.danthis.backend.application.user.request.UserUpdateServiceRequest;
import com.danthis.backend.application.user.response.UserFavoriteResponse.FavoriteDancerListResponse;
import com.danthis.backend.application.user.response.UserFavoriteResponse.WishListResponse;
import com.danthis.backend.application.user.response.UserInfoResponse;
import com.danthis.backend.application.user.response.UserPostsResponse;
import com.danthis.backend.application.user.response.UserPostsResponse.Pagination;
import com.danthis.backend.application.user.response.UserPostsResponse.PostDto;
import com.danthis.backend.application.user.response.UserReviewResponse;
import com.danthis.backend.application.user.response.UserReviewResponse.ReviewDto;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.danthis.backend.domain.mapping.wishlist.WishList;
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
  private final DancerReader dancerReader;
  private final UserGenreManager userGenreManager;
  private final UserGenreReader userGenreReader;
  private final UserDancerManager userDancerManager;
  private final UserDancerReader userDancerReader;
  private final WishListManager wishListManager;
  private final WishListReader wishListReader;
  private final PostReader postReader;
  private final PostManager postManager;
  private final ReviewReader reviewReader;
  private final ReviewManager reviewManager;

  @Transactional
  public void updateUserInfo(Long userId, UserUpdateServiceRequest request) {
    User user = userReader.readUserById(userId);

    user.updateNickname(request.getNickname());
    user.updateGender(request.getGender());
    user.updatePhoneNumber(request.getPhoneNumber());
    user.updateProfileImage(request.getProfileImage());

    Set<Genre> genres = userPreferenceMapper.mapToGenres(request.getPreferredGenres());
    Set<Dancer> dancers = userPreferenceMapper.mapToDancers(request.getPreferredDancers());

    userGenreManager.deleteByUser(user);
    userDancerManager.deleteByUser(user);

    Set<UserGenre> updatedGenres = UserGenre.createFromIds(user, genres);
    Set<UserDancer> updatedDancers = UserDancer.createFromIds(user, dancers);

    userGenreManager.saveAll(updatedGenres);
    userDancerManager.saveAll(updatedDancers);

    user.updatePreferredGenres(updatedGenres);
    user.updatePreferredDancers(updatedDancers);

    userManager.saveUser(user);
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
                           .preferredGenres(userGenreReader.findGenreIdsByUser(user))
                           .preferredDancers(userDancerReader.findDancerIdsByUser(user))
                           .build();
  }

  @Transactional
  public boolean isEmailRegistered(String email) {
    return userReader.existsByEmail(email);
  }

  @Transactional
  public boolean isNicknameAvailable(String nickname) {
    return userReader.isNicknameAvailable(nickname);
  }

  @Transactional
  public void addFavoriteDancer(Long userId, Long dancerId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    UserDancer userDancer = userDancerManager.toUserDancer(user, dancer);

    userDancerManager.saveUserDancer(userDancer);
  }

  @Transactional
  public void removeFavoriteDancer(Long userId, Long dancerId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    UserDancer userDancer = userDancerReader.readUserDancerByUserAndDancer(user, dancer);

    userDancerManager.deleteUserDancer(userDancer);
  }

  @Transactional
  public FavoriteDancerListResponse getFavoriteDancers(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<UserDancer> dancers = userDancerReader.readDancersByUserId(userId, pageable);
    return FavoriteDancerListResponse.from(dancers);
  }

  @Transactional
  public WishListResponse getWishList(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<WishList> wishLists = wishListReader.readWishListByUserId(userId, pageable);
    return WishListResponse.from(wishLists);
  }

  @Transactional
  public UserPostsResponse getUserPosts(Long userId, Integer page, Integer size) {
    Page<CommunityPost> posts = postReader.readPostsByUserId(userId, PageRequest.of(page, size));
    List<PostDto> postDtoList = postManager.toPostDtoList(posts.getContent());
    Pagination pagination = Pagination.builder()
                                      .currentPage(posts.getNumber())
                                      .totalPages(posts.getTotalPages())
                                      .build();

    return UserPostsResponse.from(postDtoList, pagination);
  }

  @Transactional
  public UserReviewResponse getUserReviews(Long userId, Integer page, Integer size) {
    Page<ClassReview> reviews = reviewReader.readReviewsByUserId(userId, PageRequest.of(page, size));
    List<ReviewDto> reviewDtoList = reviewManager.toReviewDtoList(reviews.getContent());
    Pagination pagination = Pagination.builder()
                                      .currentPage(reviews.getNumber())
                                      .totalPages(reviews.getTotalPages())
                                      .build();

    return UserReviewResponse.from(reviewDtoList, pagination);
  }
}

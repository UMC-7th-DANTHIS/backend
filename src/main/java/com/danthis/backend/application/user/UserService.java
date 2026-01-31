package com.danthis.backend.application.user;

import com.danthis.backend.application.community.implement.PostManager;
import com.danthis.backend.application.community.implement.PostReader;
import com.danthis.backend.application.danceclass.response.DanceClassListServiceResponse;
import com.danthis.backend.application.dancer.implement.DancerManager;
import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.dancer.response.DancerSummaryListResponse;
import com.danthis.backend.application.dancer.response.DancerSummaryListResponse.DancerSummaryResponse;
import com.danthis.backend.application.review.implement.ReviewManager;
import com.danthis.backend.application.review.implement.ReviewReader;
import com.danthis.backend.application.user.implement.UserManager;
import com.danthis.backend.application.user.implement.UserPreferenceMapper;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.application.user.implement.mapping.UserDancerManager;
import com.danthis.backend.application.user.implement.mapping.UserDancerReader;
import com.danthis.backend.application.user.implement.mapping.UserGenreManager;
import com.danthis.backend.application.user.implement.mapping.UserGenreReader;
import com.danthis.backend.application.user.implement.mapping.WishListReader;
import com.danthis.backend.application.user.request.UserUpdateServiceRequest;
import com.danthis.backend.application.user.response.UserInfoResponse;
import com.danthis.backend.application.user.response.UserPostsResponse;
import com.danthis.backend.application.user.response.UserPostsResponse.PostDto;
import com.danthis.backend.application.user.response.UserReviewResponse;
import com.danthis.backend.application.user.response.UserReviewResponse.ReviewDto;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
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
import org.springframework.data.domain.Sort;
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
  private final WishListReader wishListReader;
  private final PostReader postReader;
  private final PostManager postManager;
  private final ReviewReader reviewReader;
  private final ReviewManager reviewManager;
  private final DancerManager dancerManager;

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
                           .userId(user.getId())
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
  public boolean hasPhoneNumberByEmail(String email) {
    return userReader.hasPhoneNumberByEmail(email);
  }

  @Transactional
  public boolean isNicknameAvailable(String nickname) {
    return userReader.isNicknameAvailable(nickname);
  }

  @Transactional
  public void addFavoriteDancer(Long userId, Long dancerId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    if (userDancerReader.readUserDancerByUserAndDancer(user, dancer) != null) {
      throw new BusinessException(ErrorCode.ALREADY_FAVORITE);
    }
    UserDancer userDancer = UserDancer.from(user, dancer);

    userDancerManager.saveUserDancer(userDancer);
  }

  @Transactional
  public void removeFavoriteDancer(Long userId, Long dancerId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    UserDancer userDancer = userDancerReader.readUserDancerByUserAndDancer(user, dancer);
    if (userDancer == null) {
      throw new BusinessException(ErrorCode.NOT_FAVORITE);
    }

    userDancerManager.deleteUserDancer(userDancer);
  }

  @Transactional
  public DancerSummaryListResponse getFavoriteDancers(Long userId, Integer page, Integer size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<UserDancer> pages = userDancerReader.readDancersByUserId(userId, pageable);

    List<Dancer> candidates = dancerReader.readDancerInUserDancer(pages.getContent());
    List<DancerSummaryResponse> dancerInfos = dancerManager.toSummaryInfo(candidates);
    return DancerSummaryListResponse.from(dancerInfos, pages.getNumber(), pages.getTotalPages(),
        pages.getTotalElements());
  }

  @Transactional
  public DanceClassListServiceResponse getWishList(Long userId, Integer page, Integer size) {
    PageRequest pageable = PageRequest.of(page - 1, size);

    Page<WishList> wishlistPages = wishListReader.readWishListByUserId(userId, pageable);
    return DanceClassListServiceResponse.from(wishlistPages.map(WishList::getDanceClass));
  }

  @Transactional
  public UserPostsResponse getUserPosts(Long userId, Integer page, Integer size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<CommunityPost> posts = postReader.readPostsByUserId(userId, pageable);
    List<PostDto> postDtoList = postManager.toPostDtoList(posts.getContent());

    return UserPostsResponse.from(postDtoList, posts.getNumber(), posts.getTotalPages(),
        posts.getTotalElements());
  }

  @Transactional
  public UserReviewResponse getUserReviews(Long userId, Integer page, Integer size) {
    PageRequest pageable = PageRequest.of(
        page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    Page<ClassReview> reviews = reviewReader.readReviewsByUserId(userId, pageable);
    List<ReviewDto> reviewDtoList = reviewManager.toReviewDtoList(reviews.getContent());

    return UserReviewResponse.from(reviewDtoList, reviews.getNumber(), reviews.getTotalPages(),
        reviews.getTotalElements());
  }

  // 사용자가 댄서를 생성한 적이 있으면 true, 없으면 false 리턴
  public Boolean isDancer(Long userId) {
    Dancer dancer = dancerReader.readDancerByUserId(userId);
    return dancer != null;
  }
}

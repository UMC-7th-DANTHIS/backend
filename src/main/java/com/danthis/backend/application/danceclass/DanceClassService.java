package com.danthis.backend.application.danceclass;

import com.danthis.backend.application.danceclass.implement.DanceClassManager;
import com.danthis.backend.application.danceclass.implement.DanceClassMapper;
import com.danthis.backend.application.danceclass.implement.DanceClassReader;
import com.danthis.backend.application.danceclass.implement.mapping.DanceClassHashtagManager;
import com.danthis.backend.application.danceclass.implement.mapping.DanceClassImageManager;
import com.danthis.backend.application.danceclass.request.DanceClassCreateServiceRequest;
import com.danthis.backend.application.danceclass.request.DanceClassUpdateServiceRequest;
import com.danthis.backend.application.danceclass.response.DanceClassListServiceResponse;
import com.danthis.backend.application.danceclass.response.DanceClassReadServiceResponse;
import com.danthis.backend.application.danceclass.response.EligibleUserListServiceResponse;
import com.danthis.backend.application.danceclass.response.RegisteredUserListServiceResponse;
import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.review.implement.ReviewManager;
import com.danthis.backend.application.review.implement.ReviewReader;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.application.user.implement.mapping.WishListManager;
import com.danthis.backend.application.user.implement.mapping.WishListReader;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.hashtag.Hashtag;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.danceclasshashtag.DanceClassHashtag;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import com.danthis.backend.domain.user.User;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DanceClassService {

  private final DanceClassManager danceClassManager;
  private final DanceClassReader danceClassReader;
  private final DanceClassMapper danceClassMapper;
  private final DancerReader dancerReader;
  private final ReviewReader reviewReader;
  private final UserReader userReader;
  private final WishListManager wishListManager;
  private final WishListReader wishListReader;
  private final DanceClassImageManager danceClassImageManager;
  private final DanceClassHashtagManager danceClassHashtagManager;
  private final ReviewManager reviewManager;

  @Transactional
  public void createDanceClass(DanceClassCreateServiceRequest request, Long userId) {
    Dancer dancer = dancerReader.readDancerByUserId(userId);
    if (dancer == null) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    Genre genre = danceClassReader.readGenreById(request.getGenre());
    Set<Hashtag> hashtags = danceClassReader.readHashtagsByIds(request.getHashtags());

    DanceClass danceClass = danceClassMapper.mapToEntity(request, genre, dancer);
    danceClassManager.saveDanceClass(danceClass);

    Set<DanceClassHashtag> hashtagMappings = danceClassMapper.mapToHashtags(danceClass, hashtags);
    danceClassManager.saveDanceClassHashtags(hashtagMappings);

    if (request.getImages() != null) {
      Set<DanceClassImage> images = danceClassMapper.mapToImages(danceClass, request.getImages());
      danceClassManager.saveDanceClassImages(images);
    }
  }

  @Transactional
  public void updateDanceClass(DanceClassUpdateServiceRequest request) {
    DanceClass danceClass = danceClassReader.readDanceClassById(request.getClassId());
    Dancer dancer = dancerReader.readDancerByUserId(request.getUserId());

    if (!danceClass.getDancer().equals(dancer)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    danceClass.updateClassName(request.getClassName());
    danceClass.updatePrice(request.getPricePerSession());
    danceClass.updateDifficulty(request.getDifficulty());

    Genre genre = danceClassReader.readGenreById(request.getGenre());
    danceClass.updateGenre(genre);

    danceClass.updateDescription(request.getDescription());
    danceClass.updateTargetAudience(request.getTargetAudience());
    danceClass.updateVideoUrl(request.getVideoUrl());

    Set<Hashtag> hashtags = danceClassReader.readHashtagsByIds(request.getHashtags());
    danceClassHashtagManager.updateHashtags(danceClass, hashtags);

    danceClassImageManager.updateImages(danceClass, request.getImages());

    danceClassManager.saveDanceClass(danceClass);
  }

  @Transactional
  public void deleteDanceClass(Long classId, Long userId) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    Dancer dancer = dancerReader.readDancerByUserId(userId);

    if (!danceClass.getDancer().equals(dancer)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    danceClassImageManager.deleteImagesByDanceClass(danceClass);
    danceClassHashtagManager.deleteHashtagsByDanceClass(danceClass);
    danceClassManager.deleteDanceClassBookings(danceClass);
    reviewManager.deleteReviewsByDanceClass(danceClass);

    danceClassManager.deleteDanceClass(danceClass);
  }

  @Transactional
  public DanceClassReadServiceResponse getDanceClassDetail(Long classId) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    return danceClassMapper.toDanceClassDetailsResponse(danceClass);
  }

  @Transactional
  public DanceClassReadServiceResponse getDanceClassReviews(Long classId, Integer page,
      Integer size) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<ClassReview> reviewsPage = reviewReader.readReviewsByClassId(classId, pageable);

    return danceClassMapper.toDanceClassReviewsResponse(danceClass, reviewsPage);
  }

  @Transactional
  public DanceClassReadServiceResponse getDanceClassAverageRating(Long classId) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);

    Double averageRating = reviewReader.calculateAverageRatingByDanceClassId(classId);
    long totalReviews = reviewReader.countReviewsByDanceClassId(classId);

    return danceClassMapper.toDanceClassRatingResponse(danceClass, averageRating, totalReviews);
  }

  @Transactional
  public DanceClassListServiceResponse getDanceClassList(Long genreId, int page, int size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<DanceClass> danceClasses = danceClassReader.readDanceClasses(genreId, pageable);

    return DanceClassListServiceResponse.from(danceClasses);
  }

  @Transactional
  public void addFavoriteClass(Long userId, Long classId) {
    if (wishListReader.readWishListByUserIdAndClassId(userId, classId) != null) {
      throw new BusinessException(ErrorCode.ALREADY_FAVORITE);
    }
    User user = userReader.readUserById(userId);
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    WishList wishList = WishList.from(user, danceClass);

    wishListManager.saveWishList(wishList);
  }

  @Transactional
  public void deleteFavoriteClass(Long userId, Long classId) {
    WishList wishList = wishListReader.readWishListByUserIdAndClassId(userId, classId);
    if (wishList == null) {
      throw new BusinessException(ErrorCode.NOT_FAVORITE);
    }

    wishListManager.deleteWishList(wishList);
  }

  @Transactional
  public DanceClassListServiceResponse getDancerClasses(Long userId, Long dancerId, Integer page, Integer size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    Dancer dancer = dancerReader.readDancerByUserId(userId);
    if (dancerId != 0) {
      dancer = dancerReader.readDancerById(dancerId);
    }

    if (dancer == null) {
      throw new BusinessException(ErrorCode.DANCER_NOT_FOUND);
    }

    Page<DanceClass> danceClasses = danceClassReader.readDancerClasses(dancer.getId(), pageable);
    return DanceClassListServiceResponse.from(danceClasses);
  }

  @Transactional
  public DanceClassListServiceResponse getUserLearningClasses(Long userId, Integer page,
      Integer size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    User user = userReader.readUserById(userId);

    Page<DanceClass> danceClasses = danceClassReader.readUserLearningClasses(user, pageable);
    return DanceClassListServiceResponse.from(danceClasses);
  }

  @Transactional
  public EligibleUserListServiceResponse getEligibleUsersForDanceClass(Long classId, Long userId) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    Dancer dancer = danceClass.getDancer();

    if (!dancer.getUser().getId().equals(userId)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    List<DancerUserChat> chatUsers = danceClassReader.readChatUsersByDancer(dancer);

    List<Long> registeredUserIds = danceClassReader.readRegisteredUsersByDanceClass(danceClass)
                                                   .stream()
                                                   .map(booking -> booking.getUser().getId())
                                                   .toList();

    return danceClassMapper.toEligibleUserListResponse(dancer, chatUsers, registeredUserIds);
  }

  @Transactional
  public void registerUserToClass(Long dancerId, Long classId, Long userId) {
    Dancer dancer = dancerReader.readDancerByUserId(dancerId);
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    User user = userReader.readUserById(userId);

    if (!danceClass.getDancer().equals(dancer)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    if (danceClassReader.isUserAlreadyRegistered(danceClass, user)) {
      throw new BusinessException(ErrorCode.INVALID_BOOKING);
    }

    DanceClassBooking booking = DanceClassBooking.builder()
                                                 .user(user)
                                                 .danceClass(danceClass)
                                                 .bookingDate(java.time.LocalDateTime.now())
                                                 .isApproved(true)
                                                 .build();

    danceClassManager.saveBooking(booking);
  }

  @Transactional
  public RegisteredUserListServiceResponse getRegisteredUsers(Long classId, Long userId, int page
      , int size) {

    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    Dancer dancer = danceClass.getDancer();

    if (!dancer.getUser().getId().equals(userId)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<DanceClassBooking> bookings = danceClassReader.readRegisteredUsersByClass(danceClass, pageable);

    return danceClassMapper.toRegisteredUserListResponse(danceClass, bookings);
  }
}

package com.danthis.backend.application.danceclass;

import com.danthis.backend.api.danceclass.request.DanceClassBookingApproveRequest;
import com.danthis.backend.application.danceclass.implement.DanceClassManager;
import com.danthis.backend.application.danceclass.implement.DanceClassMapper;
import com.danthis.backend.application.danceclass.implement.DanceClassReader;
import com.danthis.backend.application.danceclass.request.DanceClassCreateServiceRequest;
import com.danthis.backend.application.danceclass.response.DanceClassBookingServiceResponse;
import com.danthis.backend.application.danceclass.response.DanceClassListServiceResponse;
import com.danthis.backend.application.danceclass.response.DanceClassReadServiceResponse;
import com.danthis.backend.application.dancer.implement.DancerReader;
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
import com.danthis.backend.domain.mapping.wishlist.WishList;
import com.danthis.backend.domain.user.User;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public void approveBooking(Long requesterId, Long classId, Long userId,
      DanceClassBookingApproveRequest request) {
    if (!request.getIsApproved()) {
      throw new BusinessException(ErrorCode.INVALID_BOOKING);
    }

    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    User user = userReader.readUserById(userId);
    DanceClassBooking booking = danceClassReader.readBookingByClassAndUser(danceClass, user);

    if (!danceClass.getDancer().getUser().getId().equals(requesterId)) {
      throw new BusinessException(ErrorCode.DANCER_FORBIDDEN_ACCESS);
    }

    danceClassManager.approveBooking(booking);
  }

  @Transactional
  public DanceClassBookingServiceResponse getApprovedBookings(Long classId) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    List<DanceClassBooking> approvedBookings = danceClassReader.readApprovedBookingsByClass(
        danceClass);

    if (approvedBookings.isEmpty()) {
      throw new BusinessException(ErrorCode.NO_APPROVED_USERS);
    }

    return DanceClassBookingServiceResponse.from(classId, approvedBookings);
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
  public DanceClassListServiceResponse getDancerClasses(Long userId, Integer page, Integer size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    Dancer dancer = dancerReader.readDancerByUserId(userId);
    if (dancer == null) {
      throw new BusinessException(ErrorCode.DANCER_NOT_FOUND);
    }

    Page<DanceClass> danceClasses = danceClassReader.readDancerClasses(dancer.getId(), pageable);
    return DanceClassListServiceResponse.from(danceClasses);
  }

  @Transactional
  public DanceClassListServiceResponse getUserLearningClasses(Long userId, Integer page, Integer size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    User user = userReader.readUserById(userId);

    Page<DanceClass> danceClasses = danceClassReader.readUserLearningClasses(user, pageable);
    return DanceClassListServiceResponse.from(danceClasses);
  }
}

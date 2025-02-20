package com.danthis.backend.application.review.implement;

import com.danthis.backend.application.review.implement.mapping.ReviewImageManager;
import com.danthis.backend.application.review.request.ReviewCreateServiceRequest;
import com.danthis.backend.application.review.request.ReviewUpdateServiceRequest;
import com.danthis.backend.application.user.response.UserReviewResponse.ReviewDto;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.classreview.classreviewimage.ClassReviewImage;
import com.danthis.backend.domain.classreview.classreviewimage.repository.ClassReviewImageRepository;
import com.danthis.backend.domain.classreview.repository.ClassReviewRepository;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import com.danthis.backend.domain.user.User;
import com.danthis.backend.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewManager {

  private final ClassReviewRepository classReviewRepository;
  private final ClassReviewImageRepository classReviewImageRepository;
  private final DanceClassRepository danceClassRepository;
  private final UserRepository userRepository;
  private final ReviewImageManager reviewImageManager;

  public DanceClass getDanceClassById(Long classId) {
    return danceClassRepository.findById(classId)
                               .orElseThrow(
                                   () -> new BusinessException(ErrorCode.DANCE_CLASS_NOT_FOUND));
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
                         .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }

  public void createClassReview(DanceClass danceClass, User user,
      ReviewCreateServiceRequest request) {
    ClassReview classReview = classReviewRepository.save(
        ClassReview.builder()
                   .danceClass(danceClass)
                   .user(user)
                   .title(request.getTitle())
                   .content(request.getContent())
                   .rating(request.getRating())
                   .build()
    );

    if (request.getReviewImages() != null && !request.getReviewImages().isEmpty()) {
      Set<ClassReviewImage> reviewImages = request.getReviewImages().stream()
                                                  .map(imageUrl -> new ClassReviewImage(classReview, imageUrl))
                                                  .collect(Collectors.toSet());

      classReview.setClassReviewImages(reviewImages);
    }

    classReviewRepository.save(classReview);
  }

  public List<ReviewDto> toReviewDtoList(List<ClassReview> reviews) {
    return reviews.stream()
                  .map(review -> ReviewDto.builder()
                                          .reviewId(review.getId())
                                          .classId(review.getDanceClass().getId())
                                          .title(review.getTitle())
                                          .content(review.getContent())
                                          .rating(review.getRating())
                                          .createdAt(review.getCreatedAt())
                                          .images(review.getClassReviewImages()
                                                        .stream()
                                                        .map(ClassReviewImage::getImageUrl)
                                                        .collect(Collectors.toSet()))
                                          .build())
                  .toList();
  }

  public void deleteReviewsByDanceClass(DanceClass danceClass) {
    reviewImageManager.deleteReviewImagesByDanceClass(danceClass);
    classReviewRepository.deleteByDanceClassId(danceClass.getId());
  }

  public void deleteReviewImagesByReviewId(Long reviewId) {
    classReviewImageRepository.deleteByReviewId(reviewId);
  }

  public void deleteReview(ClassReview review) {
    classReviewRepository.delete(review);
  }

  public void updateReview(ClassReview review, ReviewUpdateServiceRequest request) {
    review.updateTitle(request.getTitle());
    review.updateContent(request.getContent());
    review.updateRating(request.getRating());

    // 기존 이미지 삭제 후 새 이미지 등록
    reviewImageManager.deleteReviewImagesByReviewId(review.getId());

    if (request.getReviewImages() != null && !request.getReviewImages().isEmpty()) {
      classReviewImageRepository.saveAll(
          request.getReviewImages().stream()
                 .map(imageUrl -> ClassReviewImage.builder()
                                                  .classReview(review)
                                                  .imageUrl(imageUrl)
                                                  .build())
                 .collect(Collectors.toSet())
      );
    }
  }
}

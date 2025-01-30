package com.danthis.backend.application.review.implement;

import com.danthis.backend.application.review.request.ReviewCreateServiceRequest;
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
                                                  .map(imageUrl -> ClassReviewImage.builder()
                                                                                   .classReview(
                                                                                       classReview)
                                                                                   .imageUrl(
                                                                                       imageUrl)
                                                                                   .build())
                                                  .collect(Collectors.toSet());

      classReviewImageRepository.saveAll(reviewImages);
    }
  }
}

package com.danthis.backend.application.danceclass.implement;

import com.danthis.backend.application.danceclass.request.DanceClassCreateServiceRequest;
import com.danthis.backend.application.danceclass.response.DanceClassReadServiceResponse;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.classreview.classreviewimage.ClassReviewImage;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.hashtag.Hashtag;
import com.danthis.backend.domain.mapping.danceclasshashtag.DanceClassHashtag;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassMapper {

  public DanceClass mapToEntity(DanceClassCreateServiceRequest request, Genre genre,
      Dancer dancer) {
    return DanceClass.builder()
                     .className(request.getClassName())
                     .pricePerSession(request.getPricePerSession())
                     .difficulty(request.getDifficulty())
                     .genre(genre)
                     .dancer(dancer)
                     .classDescription(request.getDescription())
                     .targetAudience(request.getTargetAudience())
                     .classVideoUrl(request.getVideoUrl())
                     .isApproved(false)
                     .build();
  }

  public Set<DanceClassHashtag> mapToHashtags(DanceClass danceClass, Set<Hashtag> hashtags) {
    return hashtags.stream()
                   .map(hashtag -> DanceClassHashtag.builder()
                                                    .danceClass(danceClass)
                                                    .hashtag(hashtag)
                                                    .build())
                   .collect(Collectors.toSet());
  }

  public Set<DanceClassImage> mapToImages(DanceClass danceClass, Set<String> imageUrls) {
    return imageUrls.stream()
                    .map(url -> DanceClassImage.builder()
                                               .danceClass(danceClass)
                                               .imageUrl(url)
                                               .build())
                    .collect(Collectors.toSet());
  }

  public DanceClassReadServiceResponse toDanceClassDetailsResponse(DanceClass danceClass) {
    return DanceClassReadServiceResponse.builder()
                                        .id(danceClass.getId())
                                        .className(danceClass.getClassName())
                                        .dancer(mapDancer(danceClass))
                                        .genre(danceClass.getGenre().getId())
                                        .pricePerSession(danceClass.getPricePerSession())
                                        .difficulty(danceClass.getDifficulty())
                                        .details(mapDetails(danceClass))
                                        .build();
  }

  public DanceClassReadServiceResponse toDanceClassReviewsResponse(DanceClass danceClass,
      Page<ClassReview> reviewsPage) {
    return DanceClassReadServiceResponse.builder()
                                        .id(danceClass.getId())
                                        .className(danceClass.getClassName())
                                        .dancer(mapDancer(danceClass))
                                        .genre(danceClass.getGenre().getId())
                                        .pricePerSession(danceClass.getPricePerSession())
                                        .difficulty(danceClass.getDifficulty())
                                        .classReviews(reviewsPage.getContent().stream()
                                                                 .map(this::toClassReviewResponse)
                                                                 .collect(Collectors.toList()))
                                        .pagination(toPaginationResponse(reviewsPage))
                                        .build();
  }

  private DanceClassReadServiceResponse.Dancer mapDancer(DanceClass danceClass) {
    return DanceClassReadServiceResponse.Dancer.builder()
                                               .name(danceClass.getDancer().getDancerName())
                                               .profileImage(
                                                   danceClass.getDancer().getDancerImage())
                                               .openChatUrl(danceClass.getDancer().getOpenChatUrl())
                                               .build();
  }

  private DanceClassReadServiceResponse.Details mapDetails(DanceClass danceClass) {
    return DanceClassReadServiceResponse.Details.builder()
                                                .videoUrl(danceClass.getClassVideoUrl())
                                                .description(danceClass.getClassDescription())
                                                .targetAudience(danceClass.getTargetAudience())
                                                .hashtags(danceClass.getDanceClassHashtags()
                                                                    .stream()
                                                                    .map(
                                                                        DanceClassHashtag::getHashtag)
                                                                    .map(Hashtag::getId)
                                                                    .collect(Collectors.toList()))
                                                .danceClassImages(danceClass.getDanceClassImages()
                                                                            .stream()
                                                                            .map(
                                                                                DanceClassImage::getImageUrl)
                                                                            .collect(
                                                                                Collectors.toList()))
                                                .build();
  }

  public DanceClassReadServiceResponse.ClassReview toClassReviewResponse(ClassReview review) {
    return DanceClassReadServiceResponse.ClassReview.builder()
                                                    .id(review.getId())
                                                    .author(review.getUser().getNickname())
                                                    .title(review.getTitle())
                                                    .content(review.getContent())
                                                    .rating(review.getRating())
                                                    .createdAt(review.getCreatedAt())
                                                    .reviewImages(
                                                        review.getClassReviewImages().stream()
                                                              .map(ClassReviewImage::getImageUrl)
                                                              .collect(Collectors.toList()))
                                                    .build();
  }

  private DanceClassReadServiceResponse.Pagination toPaginationResponse(
      Page<ClassReview> reviewsPage) {
    return DanceClassReadServiceResponse.Pagination.builder()
                                                   .currentPage(reviewsPage.getNumber() + 1)
                                                   .totalPages(reviewsPage.getTotalPages())
                                                   .totalReviews(
                                                       (int) reviewsPage.getTotalElements())
                                                   .build();
  }

  public DanceClassReadServiceResponse toDanceClassRatingResponse(DanceClass danceClass,
      Double averageRating,
      long totalReviews) {
    return DanceClassReadServiceResponse.builder()
                                        .id(danceClass.getId())
                                        .className(danceClass.getClassName())
                                        .dancer(mapDancer(danceClass))
                                        .genre(danceClass.getGenre().getId())
                                        .pricePerSession(danceClass.getPricePerSession())
                                        .difficulty(danceClass.getDifficulty())
                                        .averageRating(
                                            averageRating != null ? Math.round(averageRating * 10)
                                                / 10.0
                                                : 0.0)
                                        .totalReviews(totalReviews)
                                        .build();
  }
}

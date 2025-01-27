package com.danthis.backend.application.danceclass.implement;

import com.danthis.backend.application.danceclass.request.DanceClassCreateServiceRequest;
import com.danthis.backend.application.danceclass.response.DanceClassReadServiceResponse;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.hashtag.Hashtag;
import com.danthis.backend.domain.mapping.danceclasshashtag.DanceClassHashtag;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

  public DanceClassReadServiceResponse toDanceClassReadServiceResponse(DanceClass danceClass) {
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
                                                                    .map(h -> h.getId())
                                                                    .collect(Collectors.toList()))
                                                .danceClassImages(danceClass.getDanceClassImages()
                                                                            .stream()
                                                                            .map(
                                                                                DanceClassImage::getImageUrl)
                                                                            .collect(
                                                                                Collectors.toList()))
                                                .build();
  }
}

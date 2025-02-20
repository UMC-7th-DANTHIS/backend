package com.danthis.backend.application.danceclass.response;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class DanceClassListServiceResponse {

  private int page;
  private int totalPages;
  private long totalElements;
  private List<DanceClassSummary> danceClasses;

  @Getter
  @Builder
  public static class DanceClassSummary {

    private Long id;
    private String className;
    private String dancerName;
    private String thumbnailImage;
    private String genre;
    private Set<Long> hashtagIds;
  }

  public static DanceClassListServiceResponse from(Page<DanceClass> danceClassPage) {
    return DanceClassListServiceResponse.builder()
                                        .page(danceClassPage.getNumber() + 1)
                                        .totalPages(danceClassPage.getTotalPages())
                                        .totalElements(danceClassPage.getTotalElements())
                                        .danceClasses(danceClassPage.getContent().stream()
                                                                    .map(danceClass -> DanceClassSummary.builder()
                                                                                                        .id(danceClass.getId())
                                                                                                        .className(danceClass.getClassName())
                                                                                                        .dancerName(danceClass.getDancer().getDancerName())
                                                                                                        .thumbnailImage(danceClass.getDanceClassImages()
                                                                                                                                  .stream()
                                                                                                                                  .findFirst()
                                                                                                                                  .map(DanceClassImage::getImageUrl)
                                                                                                                                  .orElse(null))
                                                                                                        .genre(danceClass.getGenre().getName())
                                                                                                        .hashtagIds(danceClass.getHashtagIds())
                                                                                                        .build())
                                                                    .toList())
                                        .build();
  }
}

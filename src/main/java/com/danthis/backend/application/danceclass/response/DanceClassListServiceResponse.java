package com.danthis.backend.application.danceclass.response;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import java.util.Comparator;
import java.util.List;
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
                                                                                                        .thumbnailImage(getFixedThumbnailImage(danceClass))
                                                                                                        .build())
                                                                    .toList())
                                        .build();
  }

  private static String getFixedThumbnailImage(DanceClass danceClass) {
    return danceClass.getDanceClassImages().stream()
                     .sorted(Comparator.comparing(DanceClassImage::getId))
                     .map(DanceClassImage::getImageUrl)
                     .findFirst()
                     .orElse(null);
  }
}

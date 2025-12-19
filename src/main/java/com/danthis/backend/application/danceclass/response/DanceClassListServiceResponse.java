package com.danthis.backend.application.danceclass.response;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.danceclass.danceclassschedule.DanceClassSchedule;
import com.danthis.backend.domain.danceclass.danceclassschedule.Week;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    private Set<String> days;
    private Set<String> dates;
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
                                                                                                        .genre(danceClass.getGenre().getName())
                                                                                                        .hashtagIds(danceClass.getHashtagIds())
                                                                                                        .days(extractDays(danceClass))
                                                                                                        .dates(extractDates(danceClass))
                                                                                                        .build())
                                                                    .toList())
                                        .build();
  }

  public static DanceClassListServiceResponse from(List<DanceClass> danceClasses) {
    return DanceClassListServiceResponse.builder()
                                        .page(1)
                                        .totalPages(1)
                                        .totalElements(danceClasses.size())
                                        .danceClasses(danceClasses.stream()
                                                                  .map(danceClass -> DanceClassSummary.builder()
                                                                                                      .id(danceClass.getId())
                                                                                                      .className(danceClass.getClassName())
                                                                                                      .dancerName(danceClass.getDancer().getDancerName())
                                                                                                      .thumbnailImage(getFixedThumbnailImage(danceClass))
                                                                                                      .genre(danceClass.getGenre().getName())
                                                                                                      .hashtagIds(danceClass.getHashtagIds())
                                                                                                      .days(extractDays(danceClass))
                                                                                                      .dates(extractDates(danceClass))
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

  private static Set<String> extractDays(DanceClass danceClass) {
    if (danceClass.getDanceClassSchedules() == null) {
      return Set.of();
    }

    return danceClass.getDanceClassSchedules().stream()
                      .map(DanceClassSchedule::getDay)
                      .filter(day -> day != null)
                      .map(Week::name)
                      .collect(Collectors.toSet());
  }

  private static Set<String> extractDates(DanceClass danceClass) {
    if (danceClass.getDanceClassSchedules() == null) {
      return Set.of();
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return danceClass.getDanceClassSchedules().stream()
                     .map(DanceClassSchedule::getDate)
                     .filter(date -> date != null)
                     .map(date -> date.format(formatter))
                     .collect(Collectors.toSet());
  }
}

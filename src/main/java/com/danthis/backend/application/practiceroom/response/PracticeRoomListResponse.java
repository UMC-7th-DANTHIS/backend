package com.danthis.backend.application.practiceroom.response;

import com.danthis.backend.domain.practiceroom.PracticeRoom;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PracticeRoomListResponse {

  private List<PracticeRoomInfo> practiceRooms;
  private Integer totalElements;


  @Getter
  @Builder
  private static class PracticeRoomInfo {

    private Long id;
    private String name;
    private Double longitude;
    private Double latitude;
  }

  public static PracticeRoomListResponse of(List<PracticeRoom> practiceRooms) {
    List<PracticeRoomInfo> practiceRoomInfos =
        practiceRooms.stream()
                     .map(
                         room -> PracticeRoomInfo.builder()
                                                 .id(room.getId())
                                                 .name(room.getName())
                                                 .latitude(room.getLatitude())
                                                 .longitude(room.getLongitude())
                                                 .build()
                     ).toList();
    
    return PracticeRoomListResponse.builder()
                                   .practiceRooms(practiceRoomInfos)
                                   .totalElements(practiceRoomInfos.size())
                                   .build();
  }
}

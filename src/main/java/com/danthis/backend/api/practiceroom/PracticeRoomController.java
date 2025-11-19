package com.danthis.backend.api.practiceroom;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.application.practiceroom.PracticeRoomService;
import com.danthis.backend.application.practiceroom.response.PracticeRoomListResponse;
import com.danthis.backend.common.security.aop.AssignOrNullCurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/practice-rooms")
@RequiredArgsConstructor
@Tag(name = "연습실 API 관련 컨트롤러")
public class PracticeRoomController {

  private final PracticeRoomService practiceRoomService;

  @Operation(summary = "주위 연습실 조회 API", description = "경도, 위도 기준 주위 연습실 정보를 조회합니다.")
  @GetMapping("/info/surround")
  @AssignOrNullCurrentUserInfo
  public ApiResponse<PracticeRoomListResponse> getPracticeRooomInfo(
      @RequestParam Double longitude,
      @RequestParam Double latitude,
      @RequestParam(defaultValue = "0.5") Double radius) {
    PracticeRoomListResponse response = practiceRoomService.getPracticeRoomsByLocation(
        longitude, latitude, radius);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "모든 연습실 조회 API")
  @GetMapping("/info/all")
  @AssignOrNullCurrentUserInfo
  public ApiResponse<PracticeRoomListResponse> getAllPracticeRooomInfo() {
    PracticeRoomListResponse response = practiceRoomService.getAllPracticeRoom();
    return ApiResponse.OK(response);
  }
}

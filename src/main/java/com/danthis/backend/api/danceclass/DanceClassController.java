package com.danthis.backend.api.danceclass;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.danceclass.request.DanceClassCreateRequest;
import com.danthis.backend.application.danceclass.DanceClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dance-classes")
@RequiredArgsConstructor
@Tag(name = "댄스 수업", description = "댄스 수업 관렴 API")
public class DanceClassController {

  private final DanceClassService danceClassService;

  @Operation(summary = "댄스 수업 등록 API", description = "새로운 댄스 수업을 등록합니다.")
  @PostMapping("/")
  public ApiResponse<Void> registerDanceClass(@RequestBody @Valid DanceClassCreateRequest request) {
    danceClassService.createDanceClass(request.toServiceRequest());
    return ApiResponse.OK(null);
  }
}

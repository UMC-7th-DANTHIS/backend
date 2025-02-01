package com.danthis.backend.api.file;

import com.danthis.backend.application.file.FileService;
import com.danthis.backend.application.file.response.GetPresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "파일 등록", description = "파일의 presignedUrl을 발급하는 API")
public class FileController {

  private final FileService fileService;

  @Operation(summary = "프로필 사진 presignedURL 발급")
  @PostMapping("/image/user")
  public GetPresignedUrlResponse getUserImagePresignedUrl(@RequestParam String fileExtension) {
    return fileService.getPresignedUrl("user", fileExtension);
  }

  @Operation(summary = "댄서 사진 presignedURL 발급")
  @PostMapping("/images/dancer")
  public List<GetPresignedUrlResponse> getDancerImagesPresignedUrls(
      @RequestParam List<String> fileExtensions) {

    List<GetPresignedUrlResponse> presignedUrls = new ArrayList<>();

    for (String fileExtension : fileExtensions) {
      presignedUrls.add(fileService.getPresignedUrl("dancer", fileExtension));
    }

    return presignedUrls;
  }

  @Operation(summary = "댄스 수업 사진 presignedURL 발급")
  @PostMapping("/images/dance-class")
  public List<GetPresignedUrlResponse> getDanceClassImagesPresignedUrls(
      @RequestParam List<String> fileExtensions) {

    List<GetPresignedUrlResponse> presignedUrls = new ArrayList<>();

    for (String fileExtension : fileExtensions) {
      presignedUrls.add(fileService.getPresignedUrl("classimage", fileExtension));
    }

    return presignedUrls;
  }

  @Operation(summary = "댄스 수업 영상 presignedURL 발급")
  @PostMapping("/video/dance-class")
  public GetPresignedUrlResponse getDanceClassVideoPresignedUrl(
      @RequestParam String fileExtension) {
    return fileService.getPresignedUrl("classvideo", fileExtension);
  }
}

package com.danthis.backend.api.dancer.request;

import com.danthis.backend.application.dancer.request.DancerUpdateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;

@Getter
public class DancerUpdateRequest {

  @NotBlank(message = "댄서 ID는 필수 입력값입니다.")
  private Long id;

  @NotBlank(message = "댄서 이름은 필수 입력값입니다.")
  @Size(max = 50, message = "댄서 이름은 최대 50자까지 가능합니다.")
  private String dancerName;

  @NotBlank(message = "인스타그램 계정은 필수 입력값입니다.")
  private String instargramId;

  @NotBlank(message = "오픈채팅방 링크는 필수 입력값입니다.")
  private String openChatUrl;

  @NotBlank(message = "한마디 소개글은 필수 입력값입니다.")
  private String bio;

  @NotBlank(message = "댄서 이력은 필수 입력값입니다.")
  private String history;

  @NotBlank(message = "주 장르는 필수 입력값입니다.")
  private Set<Long> preferredGenres;

  @NotBlank(message = "댄서 사진은 필수 입력값입니다.")
  private Set<String> dancerImages;

  public DancerUpdateServiceRequest toServiceRequest() {
    return DancerUpdateServiceRequest.builder()
                                     .id(id)
                                     .dancerName(dancerName)
                                     .instargramId(instargramId)
                                     .openChatUrl(openChatUrl)
                                     .bio(bio)
                                     .history(history)
                                     .preferredGenres(preferredGenres)
                                     .dancerImages(dancerImages)
                                     .build();
  }
}


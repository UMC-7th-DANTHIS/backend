package com.danthis.backend.api.dancer.request;

import com.danthis.backend.application.dancer.request.DancerAddServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;

@Getter
public class DancerAddRequest {

  @NotBlank(message = "댄서 이름은 필수 입력값입니다.")
  @Size(max = 50, message = "댄서 이름은 최대 50자까지 가능합니다.")
  private String dancerName;

  private String instargramId;

  private String openChatUrl;

  private String bio;

  private String history;

  private Set<Long> preferredGenres;

  private Set<String> dancerImages;

  public DancerAddServiceRequest toServiceRequest() {
    return DancerAddServiceRequest.builder()
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

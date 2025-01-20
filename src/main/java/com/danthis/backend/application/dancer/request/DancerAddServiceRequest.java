package com.danthis.backend.application.dancer.request;

import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerAddServiceRequest {

  private String dancerName;
  private String instargramId;
  private String openChatUrl;
  private String bio;
  private String history;
  private Set<Long> preferredGenres;
  private Set<String> dancerImages;
}

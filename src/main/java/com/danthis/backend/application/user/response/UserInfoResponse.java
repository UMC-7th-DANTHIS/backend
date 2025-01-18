package com.danthis.backend.application.user.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {

  private String nickname;
  private String gender;
  private String email;
  private String phoneNumber;
  private String profileImage;
  private List<Long> preferredGenres;
  private List<Long> preferredDancers;
}

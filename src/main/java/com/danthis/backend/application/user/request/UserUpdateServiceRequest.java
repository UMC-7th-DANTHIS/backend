package com.danthis.backend.application.user.request;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateServiceRequest {

  private String nickname;
  private String gender;
  private String phoneNumber;
  private String profileImage;
  private Set<Long> preferredGenres;
  private Set<Long> preferredDancers;
}

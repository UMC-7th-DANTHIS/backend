package com.danthis.backend.api.user.request;

import com.danthis.backend.application.user.request.UserUpdateServiceRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

  @NotBlank(message = "닉네임은 필수 입력값입니다.")
  @Size(max = 50, message = "닉네임은 최대 50자까지 가능합니다.")
  private String nickname;

  @Size(max = 10, message = "성별은 최대 10자까지 가능합니다.")
  private String gender;

  @NotBlank(message = "이메일은 필수 입력값입니다.")
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  private String email;

  @Size(max = 255, message = "전화번호는 최대 255자까지 가능합니다.")
  private String phoneNumber;

  private String profileImage;

  private Set<Long> preferredGenres;

  private Set<Long> preferredDancers;

  public UserUpdateServiceRequest toServiceRequest() {
    return UserUpdateServiceRequest.builder()
                                   .nickname(nickname)
                                   .gender(gender)
                                   .email(email)
                                   .phoneNumber(phoneNumber)
                                   .profileImage(profileImage)
                                   .preferredGenres(preferredGenres)
                                   .preferredDancers(preferredDancers)
                                   .build();
  }
}

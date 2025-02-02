package com.danthis.backend.api.danceclass.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DanceClassBookingApproveRequest {

  @NotNull
  private Boolean isApproved;
}

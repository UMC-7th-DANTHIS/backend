package com.danthis.backend.domain.mapping.danceclassbooking;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanceClassBooking extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_id", nullable = false)
  private DanceClass danceClass;

  @Column(nullable = false)
  private LocalDateTime bookingDate;

  @Column(nullable = false)
  private Boolean isApproved;

  public void updatedApprove(boolean b) {
    this.isApproved = true;
  }
}

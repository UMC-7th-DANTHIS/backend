package com.danthis.backend.domain.danceclass;

import com.danthis.backend.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DanceClass extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String className;

  @Column(nullable = false)
  private Integer pricePerSession;

  @Column(nullable = false)
  private Integer difficulty;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id", nullable = false)
  private Genre genre;

  @Column(nullable = false, length = 500)
  private String classDescription;

  @Column(nullable = false, length = 500)
  private String targetAudience;

  @Column
  private String classVideoUrl;

  @Column(nullable = false)
  private Boolean isApproved;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dancer_id", nullable = false)
  private Dancer dancer;
}

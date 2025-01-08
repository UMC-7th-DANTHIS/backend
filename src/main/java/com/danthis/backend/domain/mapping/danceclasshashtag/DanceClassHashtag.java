package com.danthis.backend.domain.mapping.danceclasshashtag;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.hashtag.HashTag;
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
public class DanceClassHashtag extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_id", nullable = false)
  private DanceClass danceClass;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hashtag_id", nullable = false)
  private HashTag hashtag;
}

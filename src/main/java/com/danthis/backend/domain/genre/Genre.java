package com.danthis.backend.domain.genre;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Genre extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String name;

  @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
  private Set<DanceClass> danceClasses;

  @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
  private Set<DancerGenre> dancerGenres;

  @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
  private Set<UserGenre> userGenres;
}

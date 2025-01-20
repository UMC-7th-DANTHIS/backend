package com.danthis.backend.domain.mapping.dancergenre;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import jakarta.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DancerGenre extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dancer_id")
  private Dancer dancer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id")
  private Genre genre;

  public static Set<DancerGenre> createFromIds(Dancer dancer, Set<Genre> genres) {
    return genres.stream()
                 .map(genre -> DancerGenre.builder()
                                          .dancer(dancer)
                                          .genre(genre)
                                          .build())
                 .collect(Collectors.toSet());
  }
}

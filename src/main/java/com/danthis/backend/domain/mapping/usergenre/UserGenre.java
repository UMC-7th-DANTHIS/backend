package com.danthis.backend.domain.mapping.usergenre;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class UserGenre extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id")
  private Genre genre;

  public static Set<UserGenre> createFromIds(User user, Set<Genre> genres) {
    return genres.stream()
                 .map(genre -> UserGenre.builder()
                                        .user(user)
                                        .genre(genre)
                                        .build())
                 .collect(Collectors.toSet());
  }
}

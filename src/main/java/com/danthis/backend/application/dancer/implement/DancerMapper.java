package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.genre.repository.GenreRepository;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerMapper {

  private final GenreRepository genreRepository;

  public Set<DancerImage> mapToDancerImage(Dancer dancer, Set<String> imageUrls) {
    return imageUrls.stream()
                    .map(imageUrl -> DancerImage.builder()
                                                .dancer(dancer)
                                                .imageUrl(imageUrl)
                                                .build()
                    ).collect(Collectors.toSet());
  }

  public Set<DancerGenre> mapToDancerGenre(Dancer dancer, Set<Genre> genres) {
    return genres.stream()
                 .map(genre -> DancerGenre.builder()
                                          .dancer(dancer)
                                          .genre(genre)
                                          .build())
                 .collect(Collectors.toSet());
  }

  public Set<Genre> mapToGenre(Set<Long> genreIds) {
    return genreIds.stream()
                   .map(genreRepository::findById)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .collect(Collectors.toSet());
  }
}

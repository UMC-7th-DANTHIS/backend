package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.dancer.dancerimage.repository.DancerImageRepository;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.genre.repository.GenreRepository;
import com.danthis.backend.domain.mapping.dancergenre.repository.DancerGenreRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerMapper {

  private final DancerImageRepository dancerImageRepository;
  private final DancerGenreRepository dancerGenreRepository;
  private final GenreRepository genreRepository;

  public Set<DancerImage> mapToDancerImage(Set<String> imageUrls) {
    return imageUrls.stream()
                    .map(imageUrl -> DancerImage.builder()
                                                .imageUrl(imageUrl)
                                                .build()
                    ).collect(Collectors.toSet());
  }

  public Set<Genre> mapToDancerGenre(Set<Long> genreIds) {
    return genreIds.stream()
                   .map(genreRepository::findById)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .collect(Collectors.toSet());
  }
}

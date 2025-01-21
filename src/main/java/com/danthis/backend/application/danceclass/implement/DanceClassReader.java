package com.danthis.backend.application.danceclass.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.genre.repository.GenreRepository;
import com.danthis.backend.domain.hashtag.Hashtag;
import com.danthis.backend.domain.hashtag.repository.HashtagRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassReader {

  private final GenreRepository genreRepository;
  private final HashtagRepository hashtagRepository;

  public Genre readGenreById(Long genreId) {
    return genreRepository.findById(genreId)
                          .orElseThrow(() -> new BusinessException(ErrorCode.GENRE_NOT_FOUND));
  }

  public Set<Hashtag> readHashtagsByIds(Set<Long> hashtagIds) {
    return hashtagIds.stream()
                     .map(hashtagRepository::findById)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .collect(Collectors.toSet());
  }
}

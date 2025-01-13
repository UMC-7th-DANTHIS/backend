package com.danthis.backend.domain.mapping.dancergenre;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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
}

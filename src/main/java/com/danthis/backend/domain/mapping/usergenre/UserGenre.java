package com.danthis.backend.domain.mapping.usergenre;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserGenre extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
<<<<<<< HEAD
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
=======
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genreId", nullable = false)
>>>>>>> efe4ff1 (feat: 외래키에 nullable = false 추가 (#2))
    private Genre genre;

}

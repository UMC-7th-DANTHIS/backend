package com.danthis.backend.domain.dancer;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.dancerimage.DancerImage;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "dancers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Dancer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name="userId")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false, length = 50)
    private String dancerName;

    @Column(nullable = false, length = 50)
    private String instargramId;

    @Column(nullable = false, length = 10)
    private String bio;

    @Column(nullable = false, length = 10)
    private String history;

    private String dancerImage;

    private String openChatUrl;

    private Boolean isApproved;

    @OneToMany(mappedBy = "dancer", fetch = FetchType.LAZY)
    private Set<DancerGenre> dancerGenres;

    @OneToMany(mappedBy = "dancer", fetch = FetchType.LAZY)
    private Set<DancerImage> dancerImages;
}

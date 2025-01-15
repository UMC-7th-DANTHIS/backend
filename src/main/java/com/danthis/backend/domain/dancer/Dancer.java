package com.danthis.backend.domain.dancer;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Dancer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id")
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

  @OneToMany(mappedBy = "dancer", fetch = FetchType.LAZY)
  private Set<DanceClass> danceClasses;

  @OneToMany(mappedBy = "dancer", fetch = FetchType.LAZY)
  private Set<UserDancer> userDancers;
}

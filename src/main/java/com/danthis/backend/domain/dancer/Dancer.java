package com.danthis.backend.domain.dancer;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.dancer.dancerimage.DancerImage;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.mapping.dancergenre.DancerGenre;
import com.danthis.backend.domain.mapping.danceruserchat.DancerUserChat;
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
public class Dancer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id")
  @OneToOne(fetch = FetchType.LAZY)
  private User user;

  @Column(nullable = false, length = 50)
  private String dancerName;

  @Column(nullable = false, length = 20)
  private String instargramId;

  @Column(nullable = false, length = 60)
  private String bio;

  @Column(nullable = false, length = 1000)
  private String history;

  @Column(nullable = false, length = 255)
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

  @OneToMany(mappedBy = "dancer", fetch = FetchType.LAZY)
  private Set<DancerUserChat> dancerUserChats;

  public void updateDancerName(String dancerName) {
    this.dancerName = dancerName;
  }

  public void updateInstargramId(String instargramId) {
    this.instargramId = instargramId;
  }

  public void updateBio(String bio) {
    this.bio = bio;
  }

  public void updateHistory(String history) {
    this.history = history;
  }

  public void updateOpenChatUrl(String openChatUrl) {
    this.openChatUrl = openChatUrl;
  }

  public void updateIsApproved(Boolean permission) {
    this.isApproved = permission;
  } // TODO 추후 개발

  public void updateDancerGenres(Set<DancerGenre> genres) {
    this.dancerGenres.clear();
    this.dancerGenres.addAll(genres);
  }

  public void updateDancerImages(Set<DancerImage> images) {
    this.dancerImages.clear();
    this.dancerImages.addAll(images);
  }

  public String getProfileImage() {
    return dancerImages.stream()
                       .findFirst()
                       .map(DancerImage::getImageUrl)
                       .orElse(null);
  }

  public Set<Genre> getGenres() {
    return dancerGenres.stream()
                       .map(DancerGenre::getGenre)
                       .collect(Collectors.toSet());
  }
}

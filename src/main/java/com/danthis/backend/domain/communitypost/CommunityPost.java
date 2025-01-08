package com.danthis.backend.domain.communitypost;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@Table(name = "communityPosts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CommunityPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ColumnDefault("0")
    private Integer views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private Set<CommunityComment> communityComments;
}

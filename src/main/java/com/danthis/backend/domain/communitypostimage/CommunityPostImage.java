package com.danthis.backend.domain.communitypostimage;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.communitypost.CommunityPost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "communityPostImages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CommunityPostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private CommunityPost post;
}

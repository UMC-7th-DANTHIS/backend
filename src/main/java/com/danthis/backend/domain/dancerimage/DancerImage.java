package com.danthis.backend.domain.dancerimage;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.dancer.Dancer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dancerIamges")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DancerImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dancerId")
    private Dancer dancer;
}

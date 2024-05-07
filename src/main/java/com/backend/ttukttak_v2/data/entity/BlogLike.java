package com.backend.ttukttak_v2.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

@Slf4j
@Getter
@Entity(name = "TB_BLOG_POST_LIKE")
@Table(name = "TB_BLOG_POST_LIKE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeIdx")
    private Long likeIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "postIdx", name = "postIdx", nullable = false)
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userIdx", name = "userIdx")
    private User user;

    @Column(name = "status", nullable = false)
    @ColumnDefault("true")
    private Boolean status;
}

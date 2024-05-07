package com.backend.ttukttak_v2.data.entity;

import com.backend.ttukttak_v2.data.enums.PostCategory;
import com.backend.ttukttak_v2.data.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "TB_BLOG_POST")
@Table(name = "TB_BLOG_POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postIdx")
    private Long postIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userIdx", name = "authorIdx", nullable = false)
    private User user;

    @Column(name = "postName", nullable = false, length = 100)
    private String name;

    @Lob
    @Column(name = "postText", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "postCategory", nullable = false, length = 50)
    private PostCategory category;

    @Column(name = "viewCount", nullable = false)
    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "postStatus", nullable = false, length = 10)
    private PostStatus status = PostStatus.ACTIVE;

}

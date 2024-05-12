package com.backend.ttukttak_v2.data.mysql.entity;

import com.backend.ttukttak_v2.data.mysql.enums.PostLinkType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

@Slf4j
@Getter
@Entity(name = "TB_BLOG_LINK")
@Table(name = "TB_BLOG_LINK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogLink extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "linkIdx")
    private Long linkIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "postIdx", name = "postIdx", nullable = false)
    private Post post;

    @Lob
    @Column(name = "link", nullable = false)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "linkType", nullable = false, length = 10)
    private PostLinkType type;

    @Column(name = "status", nullable = false)
    @ColumnDefault("true")
    private Boolean status;
}

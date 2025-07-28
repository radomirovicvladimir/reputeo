package com.reputeo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts", indexes = @Index(name = "idx_posts_active", columnList = "isDeleted, createdAt"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @Column(length = 2048)
    private String externalLink;

    @Column(length = 2048)
    private String keanuMoment;

    @Column(nullable = false, length = 100)
    @Builder.Default
    private String author = "Anonymous";

    @Column(nullable = false, length = 2048)
    @Builder.Default
    private String profilePicture = "/default-anonymous.png";

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public String getContent() {
        return isDeleted ? "[deleted]" : content;
    }

    public String getAuthor() {
        return isDeleted ? "[deleted]" : author;
    }

    public String getProfilePicture() {
        return isDeleted ? "/default-deleted.png" : profilePicture;
    }

    public String getExternalLink() {
        return isDeleted ? null : externalLink;
    }

    public String getKeanuMoment() {
        return isDeleted ? null : keanuMoment;
    }
}
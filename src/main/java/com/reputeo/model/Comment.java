package com.reputeo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments", indexes = {
        @Index(name = "idx_comments_post_parent", columnList = "post_id, parentComment_id"), // new
        @Index(name = "idx_comments_parent", columnList = "parentComment_id"), // new
        @Index(name = "idx_comments_created", columnList = "createdAt") // new
                                    })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 100)
    @Builder.Default
    private String author = "Anonymous";

    @Column(nullable = false, length = 2048)
    @Builder.Default
    private String profilePicture = "/default-anonymous.png";

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> replies = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;

    public String getAuthor() {
        return deleted ? "[deleted]" : author;
    }

    public String getContent() {
        return deleted ? "[deleted]" : content;
    }

    public String getProfilePicture() {
        return deleted ? "/default-deleted.png" : profilePicture;
    }
}
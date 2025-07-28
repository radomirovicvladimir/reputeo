package com.reputeo.dto.response;

import com.reputeo.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private PostType postType;
    private String externalLink;
    private String keanuMoment;
    private String author;
    private String profilePicture;
    private LocalDateTime createdAt;
    private boolean isDeleted;
}
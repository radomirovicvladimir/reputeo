package com.reputeo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private String author;
    private String profilePicture;
    private LocalDateTime createdAt;
    private List<CommentResponse> replies;
    private boolean hasMoreReplies;
}
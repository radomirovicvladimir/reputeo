package com.reputeo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank @Size(max = 5000)
    private String content;

    @Size(max = 100)
    private String author;

    @Size(max = 2048)
    private String profilePicture;

    private Long parentCommentId;
}
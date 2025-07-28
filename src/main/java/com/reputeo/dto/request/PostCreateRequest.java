package com.reputeo.dto.request;

import com.reputeo.model.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostCreateRequest {
    @NotBlank @Size(max = 300)
    private String title;

    @NotBlank @Size(max = 10000)
    private String content;

    @NotNull
    private PostType postType;

    @Size(max = 2048)
    private String externalLink;

    @Size(max = 100)
    private String author = "Anonymous";;

    @Size(max = 2048)
    private String profilePicture = "/default-anonymous.png";
}
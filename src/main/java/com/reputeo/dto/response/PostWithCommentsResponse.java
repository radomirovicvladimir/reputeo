package com.reputeo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostWithCommentsResponse {
    private PostResponse post;
    private List<CommentResponse> comments;
    private boolean hasMoreComments;
}
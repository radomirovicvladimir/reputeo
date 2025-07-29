package com.reputeo.controller;

import com.reputeo.dto.request.CommentRequest;
import com.reputeo.dto.response.CommentResponse;
import com.reputeo.model.Comment;
import com.reputeo.service.CommentService;
import com.reputeo.service.RecaptchaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final RecaptchaService recaptchaService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<?> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request,
            @RequestParam("recaptchaToken") String recaptchaToken
    ) {
        if (!recaptchaService.verifyToken(recaptchaToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid reCAPTCHA token");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(postId, request));
    }

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<Page<CommentResponse>> getComments(
            @PathVariable Long postId,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(commentService.getNestedComments(postId, pageable));
    }

    @GetMapping("/api/comments/{commentId}/replies")
    public ResponseEntity<Page<CommentResponse>> getCommentReplies(
            @PathVariable Long commentId,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(commentService.getDirectReplies(commentId, pageable));
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentId, request));
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> softDeleteComment(
            @PathVariable Long commentId
    ) {
        commentService.softDeleteComment(commentId);
        return ResponseEntity.noContent().build();
    }


}
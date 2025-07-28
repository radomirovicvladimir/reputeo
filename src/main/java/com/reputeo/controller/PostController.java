package com.reputeo.controller;

import com.reputeo.dto.request.*;
import com.reputeo.dto.response.*;
import com.reputeo.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(request));
    }

    @GetMapping("/search/{author}")
    public ResponseEntity<Page<PostSummaryResponse>> getPostsByAuthor(
            @PathVariable String author,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getPostsByAuthor(author, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<PostSummaryResponse>> getAllPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostWithCommentsResponse> getPostWithComments(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getPostWithComments(id, pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePostContent(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(postService.updatePostContent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeletePost(@PathVariable Long id) {
        postService.softDeletePost(id);
        return ResponseEntity.noContent().build();
    }
}
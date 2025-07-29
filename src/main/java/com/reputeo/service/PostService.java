package com.reputeo.service;

import com.reputeo.dto.request.PostCreateRequest;
import com.reputeo.dto.request.PostUpdateRequest;
import com.reputeo.dto.response.*;
import com.reputeo.exception.ResourceNotFoundException;
import com.reputeo.mapper.PostMapper;
import com.reputeo.model.Comment;
import com.reputeo.model.Post;
import com.reputeo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final KeanuService keanuService;
    private final CommentService commentService;

    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        Post post = postMapper.toEntity(request);
        post.setKeanuMoment(keanuService.getRandomWhoaMoment());
        Post savedPost = postRepository.save(post);
        return postMapper.toResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public Page<PostSummaryResponse> getAllPosts(Pageable pageable) {
        return postRepository.findByIsDeletedFalseOrderByCreatedAtDesc(pageable)
                .map(postMapper::toSummaryResponse);
    }

    @Transactional(readOnly = true)
    public Page<PostSummaryResponse> getPostsByAuthor(String author, Pageable pageable) {
        return postRepository.findByAuthorAndIsDeletedFalseOrderByCreatedAtDesc(author, pageable)
                .map(postMapper::toSummaryResponse);
    }

    @Transactional(readOnly = true)
    public PostWithCommentsResponse getPostWithComments(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Page<CommentResponse> commentsPage = commentService.getNestedComments(postId, pageable);

        return PostWithCommentsResponse.builder()
                .post(postMapper.toResponse(post))
                .comments(commentsPage.getContent())
                .hasMoreComments(commentsPage.hasNext())
                .build();
    }

    @Transactional
    public PostResponse updatePostContent(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (post.isDeleted()) {
            throw new IllegalStateException("Cannot update a deleted post");
        }

        post.setContent(request.getContent());
        Post updatedPost = postRepository.save(post);
        return postMapper.toResponse(updatedPost);
    }

    @Transactional
    public void softDeletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setDeleted(true);

        postRepository.save(post);
    }
}
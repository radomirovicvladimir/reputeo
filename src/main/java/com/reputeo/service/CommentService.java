package com.reputeo.service;

import com.reputeo.dto.request.CommentRequest;
import com.reputeo.dto.response.CommentResponse;
import com.reputeo.exception.ResourceNotFoundException;
import com.reputeo.mapper.CommentMapper;
import com.reputeo.model.Comment;
import com.reputeo.model.Post;
import com.reputeo.repository.CommentRepository;
import com.reputeo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private static final int MAX_NESTING = 5;

    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment parent = null;
        if (request.getParentCommentId() != null) {
            parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found"));

            if (!parent.getPost().getId().equals(postId)) {
                throw new IllegalArgumentException("Parent comment doesn't belong to this post");
            }
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(request.getAuthor() != null ? request.getAuthor() : "Anonymous")
                .profilePicture(request.getProfilePicture() != null ? request.getProfilePicture() : "/default-anonymous.png")
                .post(post)
                .parentComment(parent)
                .build();

        comment = commentRepository.save(comment);

        return buildMinimalCommentResponse(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getNestedComments(Long postId, Pageable pageable) {
        Page<Comment> topLevelPage = commentRepository.findTopLevelByPostId(postId, pageable);

        List<CommentResponse> responses = topLevelPage.getContent().stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());

        buildNestedReplies(responses, 1);

        return new PageImpl<>(
                responses,
                pageable,
                topLevelPage.getTotalElements()
        );
    }

    private void buildNestedReplies(List<CommentResponse> commentResponses, int currentLevel) {
        if (currentLevel > MAX_NESTING || commentResponses.isEmpty()) {
            return;
        }

        List<Long> parentIds = commentResponses.stream()
                .map(CommentResponse::getId)
                .collect(Collectors.toList());

        List<Comment> replies = commentRepository.findDirectRepliesByParentIds(parentIds);
        Map<Long, List<Comment>> repliesMap = replies.stream()
                .collect(Collectors.groupingBy(
                        reply -> reply.getParentComment().getId()
                ));

        Map<Long, CommentResponse> responseMap = new HashMap<>();
        for (CommentResponse parent : commentResponses) {
            List<Comment> childComments = repliesMap.getOrDefault(parent.getId(), new ArrayList<>());
            List<CommentResponse> childResponses = childComments.stream()
                    .map(commentMapper::toResponse)
                    .collect(Collectors.toList());

            parent.setReplies(childResponses);
            parent.setHasMoreReplies(commentRepository.hasMoreReplies(parent.getId()));

            for (CommentResponse child : childResponses) {
                responseMap.put(child.getId(), child);
            }
        }

        if (!responseMap.isEmpty() && currentLevel < MAX_NESTING) {
            buildNestedReplies(new ArrayList<>(responseMap.values()), currentLevel + 1);
        }
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getDirectReplies(Long parentId, Pageable pageable) {
        Page<Comment> repliesPage = commentRepository.findDirectRepliesByParentId(parentId, pageable);

        return repliesPage.map(comment -> {
            CommentResponse response = commentMapper.toResponse(comment);
            response.setReplies(new ArrayList<>());

            // Check if this reply has its own replies
            boolean hasReplies = commentRepository.countByParentCommentId(comment.getId()) > 0;
            response.setHasMoreReplies(hasReplies);

            return response;
        });
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (comment.isDeleted()) {
            throw new IllegalStateException("Cannot update a deleted comment");
        }

        comment.setContent(request.getContent());
        if (request.getAuthor() != null) {
            comment.setAuthor(request.getAuthor());
        }
        if (request.getProfilePicture() != null) {
            comment.setProfilePicture(request.getProfilePicture());
        }

        comment = commentRepository.save(comment);
        return buildMinimalCommentResponse(comment);
    }

    private CommentResponse buildMinimalCommentResponse(Comment comment) {
        CommentResponse response = commentMapper.toResponse(comment);
        response.setReplies(new ArrayList<>());
        response.setHasMoreReplies(false);
        return response;
    }

    @Transactional
    public void softDeleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        comment.setDeleted(true);

        commentRepository.save(comment);
    }
}
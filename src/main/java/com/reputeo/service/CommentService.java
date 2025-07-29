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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private static final int MAX_NESTING = 5;

    @Transactional
    public Comment createComment(Long postId, CommentRequest request) {
        logger.info("Creating comment on postId={} with parentCommentId={}", postId, request.getParentCommentId());

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

        return comment;
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getNestedComments(Long postId, Pageable pageable) {
        logger.info("Fetching nested comments for postId={} with pageable={}", postId, pageable);
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
        logger.debug("Building nested replies at level {} for {} comments", currentLevel, commentResponses.size());
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
            parent.setHasMoreReplies(!childResponses.isEmpty());


            for (CommentResponse child : childResponses) {
                responseMap.put(child.getId(), child);
            }
        }

        if (!responseMap.isEmpty() && currentLevel < MAX_NESTING) {
            buildNestedReplies(new ArrayList<>(responseMap.values()), currentLevel + 1);
        }
        else{
            for (CommentResponse child : responseMap.values()) {
                child.setReplies(null);
                child.setHasMoreReplies(commentRepository.hasMoreReplies(child.getId()));
            }
        }

    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getDirectReplies(Long parentId, Pageable pageable) {
        logger.info("Fetching direct replies for parentId={} with pageable={}", parentId, pageable);
        Page<Comment> repliesPage = commentRepository.findDirectRepliesByParentId(parentId, pageable);

        return repliesPage.map(comment -> {
            CommentResponse response = commentMapper.toResponse(comment);

            response.setReplies(new ArrayList<>());
            response.setHasMoreReplies(commentRepository.hasMoreReplies(comment.getId()));

            return response;
        });
    }

    @Transactional
    public Comment updateComment(Long commentId, CommentRequest request) {
        logger.info("Updating comment with id={}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (comment.isDeleted()) {
            logger.warn("Attempted to update a deleted comment with id={}", commentId);
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

        return comment;
    }

    @Transactional
    public void softDeleteComment(Long commentId) {
        logger.info("Soft deleting comment with id={}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        comment.setDeleted(true);

        commentRepository.save(comment);
    }
}
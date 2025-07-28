package com.reputeo.repository;

import com.reputeo.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parentComment IS NULL")
    Page<Comment> findTopLevelByPostId(
            @Param("postId") Long postId,
            Pageable pageable
    );

    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentId")
    Page<Comment> findDirectRepliesByParentId(
            @Param("parentId") Long parentId,
            Pageable pageable
    );

    @Query("SELECT COUNT(c) > 0 FROM Comment c WHERE c.parentComment.id = :parentId")
    boolean hasMoreReplies(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Comment c WHERE c.parentComment.id IN :parentIds")
    List<Comment> findDirectRepliesByParentIds(
            @Param("parentIds") List<Long> parentIds
    );

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.parentComment.id = :parentId")
    long countByParentCommentId(@Param("parentId") Long parentId);
}
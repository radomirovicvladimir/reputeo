package com.reputeo.repository;

import com.reputeo.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByIsDeletedFalseOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByAuthorAndIsDeletedFalseOrderByCreatedAtDesc(
            @Param("author") String author,
            Pageable pageable
    );


}
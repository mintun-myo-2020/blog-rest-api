package com.springboot.blog.blogrestapi.repository;

import com.springboot.blog.blogrestapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);

}
